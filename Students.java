package LibraryManagementSystem;

import java.sql.*;
import java.util.Scanner;

public class Students implements Member {

    static final String url="jdbc:mysql://localhost:3306/library_db";
    static final String username="root";
    static final String password="Shiva@165";

    @Override
    public void registerMember() {

            try (Connection connection=DriverManager.getConnection(url, username, password)) {

                System.out.println("Connected to the Database!");

                Scanner scanner = new Scanner(System.in);

                System.out.print("Enter Student ID: ");
                int studentId = scanner.nextInt();
                scanner.nextLine();

                System.out.print("Enter Student Name: ");
                String name = scanner.nextLine();

                System.out.print("Enter Email: ");
                String email = scanner.nextLine();

                System.out.print("Enter Phone No: ");
                String phoneNo = scanner.nextLine();

                System.out.print("Enter Department: ");
                String department = scanner.nextLine();

                String insertSQL = "INSERT INTO Student " + "(studentId, name, email, phoneNo, department) " + "VALUES (?, ?, ?, ?, ?)";

                try (PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {

                    preparedStatement.setInt(1, studentId);
                    preparedStatement.setString(2, name);
                    preparedStatement.setString(3, email);
                    preparedStatement.setString(4, phoneNo);
                    preparedStatement.setString(5, department);

                    int rowsInserted = preparedStatement.executeUpdate();

                    if (rowsInserted > 0) {
                        System.out.println("Student registered successfully!");
                    }

                } catch (SQLException e) {
                    System.err.println("Insert error: " + e.getMessage());
                }

            } catch (SQLException e) {
                System.err.println("Database connection error: " + e.getMessage());
            }
    }

    @Override
    public void searchMember() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter member ID to search: ");
        int studentId = scanner.nextInt();

        String searchSQL = "SELECT * FROM Student WHERE studentId=?";

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = connection.prepareStatement(searchSQL)) {

            ps.setInt(1, studentId);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {

                System.out.println("\n========== STUDENT FOUND ==========");

                System.out.println("Member ID: " + rs.getInt("studentId"));
                System.out.println("name: " + rs.getString("name"));
                System.out.println("email: " + rs.getString("email"));
                System.out.println("phoneNo: " + rs.getString("phoneNo"));
                System.out.println("================================");

            } else {
                System.out.println("No Member found with student ID: " + studentId);
            }

        } catch (SQLException e) {
            System.out.println("Search error: " + e.getMessage());
        }

    }

    @Override
    public void updateMember() {
        try (
                Connection connection = DriverManager.getConnection(url, username, password);
                Scanner scanner = new Scanner(System.in)
        ) {

            System.out.print("Enter member ID to update: ");
            int studentId = scanner.nextInt();
            scanner.nextLine();

            System.out.print("Enter new member name: ");
            String name = scanner.nextLine();

            System.out.print("Enter new email: ");
            String email = scanner.nextLine();

            System.out.print("Enter new phone number: ");
            double phoneNo = scanner.nextDouble();

            String updateSQL = "UPDATE Student SET name=?, email=?, phoneNo=? WHERE studentId=?";

            try (PreparedStatement ps = connection.prepareStatement(updateSQL)) {

                ps.setString(1, name);
                ps.setString(2, email);
                ps.setDouble(3, phoneNo);
                ps.setInt(4, studentId);

                int rowsUpdated = ps.executeUpdate();

                if (rowsUpdated > 0) {
                    System.out.println("student updated successfully!");
                } else {
                    System.out.println("No record found with student ID: " + studentId);
                }

            } catch (SQLException e) {
                System.out.println("Update error: " + e.getMessage());
            }

        } catch (SQLException e) {
            System.out.println("Database connection error: " + e.getMessage());
        }


    }

    @Override
    public void deleteMember() {
        try (Connection connection = DriverManager.getConnection(url,username,password)){
            System.out.println("Connected to the Database!");

            Scanner scanner = new Scanner(System.in);

            System.out.print("Enter Student ID to delete: ");
            int studentId = scanner.nextInt();

            String deleteSQL = "Delete from Student WHERE studentId=?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(deleteSQL)) {
                preparedStatement.setInt(1, studentId);

                int rowsDeleted = preparedStatement.executeUpdate();

                if (rowsDeleted > 0) {
                    System.out.println("Record deleted successfully!!");
                } else {
                    System.out.println("No record found with student ID: " + studentId);
                }
            } catch(SQLException e) {
                System.err.println("Update error:" +e.getMessage());
            }
        } catch (SQLException e) {
            System.err.println("Datebase connection error: "+ e.getMessage());
        }

    }

    @Override
    public void displayMember() {
        String query = "SELECT * FROM Student";

        try (Connection conn = DriverManager.getConnection(url, username, password);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            ResultSetMetaData metaData = rs.getMetaData();

            int columnCount = metaData.getColumnCount();

            for (int i = 1; i <= columnCount; i++) {
                System.out.print(metaData.getColumnName(i) + "\t");
            }

            System.out.println();

            while (rs.next()) {
                for (int i = 1; i <= columnCount; i++) {
                    System.out.print(rs.getString(i) + "\t");
                }

                System.out.println();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
