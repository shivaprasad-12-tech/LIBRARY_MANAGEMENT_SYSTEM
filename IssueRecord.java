package LibraryManagementSystem;

import java.sql.*;
import java.time.LocalDate;
import java.util.Scanner;

public class IssueRecord {
    static final String url="jdbc:mysql://localhost:3306/library_db";
    static final String username="root";
    static final String password="Shiva@165";

    public void issueBook() {
        Scanner scanner=new Scanner(System.in);
        try (Connection connection=DriverManager.getConnection(url, username, password)) {
            System.out.println("Connected to the Database!");

            System.out.print("Enter Book ID: ");
            int bookId=scanner.nextInt();

            System.out.print("Enter 1 for Student or 2 for Faculty: ");
            int memberType=scanner.nextInt();

            long studentId=0;
            int facultyId=0;

            if (memberType==1) {
                System.out.print("Enter Student ID: ");
                studentId=scanner.nextLong();
            } else if (memberType==2) {
                System.out.print("Enter Faculty ID: ");
                facultyId=scanner.nextInt();
            } else {
                System.out.println("Invalid member type!");
                return;
            }

            String bookQuery="SELECT availableQuantity FROM Book WHERE bookId=?";

            try (PreparedStatement ps=connection.prepareStatement(bookQuery)) {
                ps.setInt(1, bookId);
                ResultSet rs = ps.executeQuery();
                if (!rs.next()) {
                    System.out.println("No book found with Book ID: " + bookId);
                    return;
                }

                int availableQuantity=rs.getInt("availableQuantity");

                if (availableQuantity<=0) {
                    System.out.println("Book is currently not available!");
                    return;
                }
            }

            if (memberType==1) {
                String studentQuery="SELECT studentId FROM Student WHERE studentId=?";
                try (PreparedStatement ps=connection.prepareStatement(studentQuery)) {
                    ps.setLong(1, studentId);
                    ResultSet rs = ps.executeQuery();
                    if (!rs.next()) {
                        System.out.println("Student not found!");
                        return;
                    }
                }
            }

            if (memberType==2) {
                String facultyQuery="SELECT facultyId FROM Faculty WHERE facultyId=?";

                try (PreparedStatement ps=connection.prepareStatement(facultyQuery)) {
                    ps.setInt(1, facultyId);
                    ResultSet rs = ps.executeQuery();
                    if (!rs.next()) {
                        System.out.println("Faculty not found!");
                        return;
                    }
                }
            }

            LocalDate issueDate=LocalDate.now();

            System.out.println("Issue Date: " + issueDate);

            System.out.print("Enter number of days for borrowing: ");
            int days=scanner.nextInt();

            LocalDate dueDate=issueDate.plusDays(days);

            String insertSQL="INSERT INTO IssueRecord " + "(bookId, studentId, facultyId, issueDate, dueDate, status) " + "VALUES (?, ?, ?, ?, ?, ?)";

            try (PreparedStatement ps=connection.prepareStatement(insertSQL)) {
                ps.setInt(1, bookId);

                if (memberType==1) {
                    ps.setLong(2, studentId);
                    ps.setNull(3, Types.INTEGER);
                } else {
                    ps.setNull(2, Types.BIGINT);
                    ps.setInt(3, facultyId);
                }

                ps.setDate(4, Date.valueOf(issueDate));
                ps.setDate(5, Date.valueOf(dueDate));
                ps.setString(6, "ISSUED");

                int rowsInserted=ps.executeUpdate();

                if (rowsInserted>0) {
                    // Decrease available quantity
                    String updateBook="UPDATE Book " + "SET availableQuantity = availableQuantity - 1 " + "WHERE bookId=?";

                    try (PreparedStatement updatePS=connection.prepareStatement(updateBook)) {
                        updatePS.setInt(1, bookId);
                        updatePS.executeUpdate();
                    }

                    System.out.println("Book issued successfully!");
                    System.out.println("Issue Date: " + issueDate);
                    System.out.println("Due Date: " + dueDate);
                }
            }

        } catch (SQLException e) {
            System.out.println("Issue error: " + e.getMessage());
        }
    }

    public void returnBook() {
        Scanner scanner=new Scanner(System.in);

        try (Connection connection=DriverManager.getConnection(url, username, password)) {

            System.out.print("Enter Issue ID: ");
            int issueId=scanner.nextInt();

            String findIssue="SELECT bookId, dueDate, status " + "FROM IssueRecord WHERE issueId=?";

            try (PreparedStatement ps=connection.prepareStatement(findIssue)) {
                ps.setInt(1, issueId);
                ResultSet rs = ps.executeQuery();

                if (!rs.next()) {
                    System.out.println("Issue record not found!");
                    return;
                }

                int bookId = rs.getInt("bookId");
                String status = rs.getString("status");

                if (status.equals("RETURNED")) {
                    System.out.println("This book has already been returned!");
                    return;
                }

                // Update IssueRecord
                String updateIssue="UPDATE IssueRecord " + "SET returnDate=?, status=? " + "WHERE issueId=?";

                try (PreparedStatement updatePS=connection.prepareStatement(updateIssue)) {
                    updatePS.setDate(1, Date.valueOf(LocalDate.now()));
                    updatePS.setString(2, "RETURNED");
                    updatePS.setInt(3, issueId);
                    updatePS.executeUpdate();
                }

                // Increase available quantity
                String updateBook="UPDATE Book " + "SET availableQuantity = availableQuantity + 1 " + "WHERE bookId=?";

                try (PreparedStatement bookPS=connection.prepareStatement(updateBook)) {
                    bookPS.setInt(1, bookId);
                    bookPS.executeUpdate();
                }

                System.out.println("Book returned successfully!");
            }

        } catch (SQLException e) {
            System.out.println("Return error: " + e.getMessage());
        }
    }

    public void renewBook() {
        Scanner scanner = new Scanner(System.in);

        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            System.out.print("Enter Issue ID: ");
            int issueId = scanner.nextInt();

            System.out.print("Enter additional days: ");
            int days = scanner.nextInt();

            String query = "SELECT dueDate, status " + "FROM IssueRecord WHERE issueId=?";

            try (PreparedStatement ps = connection.prepareStatement(query)) {
                ps.setInt(1, issueId);
                ResultSet rs = ps.executeQuery();

                if (!rs.next()) {
                    System.out.println("Issue record not found!");
                    return;
                }

                String status = rs.getString("status");

                if (status.equals("RETURNED")) {
                    System.out.println("Cannot renew a returned book!");
                    return;
                }

                Date oldDueDate = rs.getDate("dueDate");
                LocalDate newDueDate = oldDueDate.toLocalDate().plusDays(days);

                String updateSQL = "UPDATE IssueRecord " + "SET dueDate=? WHERE issueId=?";

                try (PreparedStatement updatePS = connection.prepareStatement(updateSQL)) {
                    updatePS.setDate(1, Date.valueOf(newDueDate));
                    updatePS.setInt(2, issueId);
                    updatePS.executeUpdate();

                    System.out.println("Book renewed successfully!");
                    System.out.println("New Due Date: " + newDueDate);
                }
            }

        } catch (SQLException e) {
            System.out.println("Renew error: " + e.getMessage());
        }
    }

    public void displayIssuedBooks() {
        String query="SELECT * FROM IssueRecord " + "WHERE status='ISSUED'";

        try (Connection connection=DriverManager.getConnection(url, username, password);
             Statement stmt=connection.createStatement();
             ResultSet rs=stmt.executeQuery(query)) {

            System.out.println("\n========== ISSUED BOOKS ==========");

            while (rs.next()) {
                System.out.println("Issue ID: " + rs.getInt("issueId"));
                System.out.println("Book ID: " + rs.getInt("bookId"));
                System.out.println("Student ID: " + rs.getObject("studentId"));
                System.out.println("Faculty ID: " + rs.getObject("facultyId"));
                System.out.println("Issue Date: " + rs.getDate("issueDate"));
                System.out.println("Due Date: " + rs.getDate("dueDate"));
                System.out.println("Status: " + rs.getString("status"));
                System.out.println("--------------------------------");
            }

        } catch (SQLException e) {
            System.out.println("Display error: " + e.getMessage());
        }
    }

    public void displayOverdueBooks() {
        String query="SELECT * FROM IssueRecord " + "WHERE status='ISSUED' " + "AND dueDate < CURDATE()";

        try (Connection connection=DriverManager.getConnection(url, username, password);
             Statement stmt=connection.createStatement();
             ResultSet rs=stmt.executeQuery(query)) {

            System.out.println("\n========== OVERDUE BOOKS ==========");

            boolean found = false;

            while (rs.next()) {
                found = true;

                System.out.println("Issue ID: " + rs.getInt("issueId"));
                System.out.println("Book ID: " + rs.getInt("bookId"));
                System.out.println("Student ID: " + rs.getObject("studentId"));
                System.out.println("Faculty ID: " + rs.getObject("facultyId"));
                System.out.println("Issue Date: " + rs.getDate("issueDate"));
                System.out.println("Due Date: " + rs.getDate("dueDate"));
                System.out.println("Status: " + rs.getString("status"));
                System.out.println("--------------------------------");
            }

            if (!found) {
                System.out.println("No overdue books found.");
            }

        } catch (SQLException e) {
            System.out.println("Overdue search error: " + e.getMessage());
        }
    }
}
