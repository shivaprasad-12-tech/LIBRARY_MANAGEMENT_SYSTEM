package LibraryManagementSystem;

import java.sql.*;
import java.util.Scanner;

public class Faculty implements Member {

    static final String url="jdbc:mysql://localhost:3306/library_db";
    static final String username="root";
    static final String password="Shiva@165";


    @Override
    public void registerMember() {
        try (Connection connection=DriverManager.getConnection(url, username, password)) {
            System.out.println("Connected to the Database!");

            Scanner scanner=new Scanner(System.in);

            System.out.print("Enter Faculty ID: ");
            int facultyId=scanner.nextInt();
            scanner.nextLine();

            System.out.print("Enter Faculty Name: ");
            String name=scanner.nextLine();

            System.out.print("Enter Email: ");
            String email=scanner.nextLine();

            System.out.print("Enter Phone No: ");
            String phoneNo=scanner.nextLine();

            System.out.print("Enter Department: ");
            String department=scanner.nextLine();

            System.out.print("Enter Branch: ");
            String branch=scanner.nextLine();

            System.out.print("Enter Faculty Type (Teaching/Non-Teaching): ");
            String facultyType=scanner.nextLine();

            System.out.print("Enter Designation: ");
            String designation=scanner.nextLine();


            String insertSQL ="INSERT INTO Faculty " + "(facultyId, name, email, phoneNo, department, branch, facultyType, designation) " + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";


            try (PreparedStatement preparedStatement=connection.prepareStatement(insertSQL)) {
                preparedStatement.setInt(1, facultyId);
                preparedStatement.setString(2, name);
                preparedStatement.setString(3, email);
                preparedStatement.setString(4, phoneNo);
                preparedStatement.setString(5, department);
                preparedStatement.setString(6, branch);
                preparedStatement.setString(7, facultyType);
                preparedStatement.setString(8, designation);

                int rowsInserted=preparedStatement.executeUpdate();

                if (rowsInserted > 0) {
                    System.out.println("Faculty registered successfully!");
                }

            } catch (SQLException e) {
                System.err.println("Insert error: " + e.getMessage());
            }

        } catch (SQLException e) {
            System.err.println("Database connection error: " + e.getMessage());
        }
    }


    @Override
    public void updateMember() {
        try (Connection connection=DriverManager.getConnection(url, username, password);
                Scanner scanner = new Scanner(System.in)
        ) {

            System.out.print("Enter Faculty ID to update: ");
            int facultyId=scanner.nextInt();
            scanner.nextLine();

            System.out.print("Enter new Faculty Name: ");
            String name=scanner.nextLine();

            System.out.print("Enter new Email: ");
            String email=scanner.nextLine();

            System.out.print("Enter new Phone Number: ");
            String phoneNo=scanner.nextLine();

            System.out.print("Enter new Department: ");
            String department=scanner.nextLine();

            System.out.print("Enter new Branch: ");
            String branch=scanner.nextLine();

            System.out.print("Enter new Faculty Type (Teaching/Non-Teaching): ");
            String facultyType=scanner.nextLine();

            System.out.print("Enter new Designation: ");
            String designation=scanner.nextLine();


            String updateSQL = "UPDATE Faculty SET " + "name=?, " + "email=?, " + "phoneNo=?, " + "department=?, " + "branch=?, " + "facultyType=?, " + "designation=? " + "WHERE facultyId=?";

            try (PreparedStatement ps=connection.prepareStatement(updateSQL)) {
                ps.setString(1, name);
                ps.setString(2, email);
                ps.setString(3, phoneNo);
                ps.setString(4, department);
                ps.setString(5, branch);
                ps.setString(6, facultyType);
                ps.setString(7, designation);
                ps.setInt(8, facultyId);

                int rowsUpdated=ps.executeUpdate();
                if (rowsUpdated>0) {
                    System.out.println("Faculty updated successfully!");
                } else {
                    System.out.println("No Faculty found with ID: " + facultyId);
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

        try (Connection connection=DriverManager.getConnection(url, username, password);
                Scanner scanner = new Scanner(System.in)) {

            System.out.print("Enter Faculty ID to delete: ");
            int facultyId = scanner.nextInt();
            String deleteSQL = "DELETE FROM Faculty WHERE facultyId=?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(deleteSQL)) {
                preparedStatement.setInt(1, facultyId);
                int rowsDeleted=preparedStatement.executeUpdate();
                if (rowsDeleted > 0) {
                    System.out.println("Faculty deleted successfully!");
                } else {
                    System.out.println("No Faculty found with ID: " + facultyId);
                }

            } catch (SQLException e) {
                System.err.println("Delete error: " + e.getMessage());
            }

        } catch (SQLException e) {
            System.err.println("Database connection error: " + e.getMessage());
        }
    }

    @Override
    public void searchMember() {
        Scanner scanner=new Scanner(System.in);
        System.out.print("Enter Faculty ID to search: ");
        int facultyId = scanner.nextInt();

        String searchSQL="SELECT * FROM Faculty WHERE facultyId=?";


        try (Connection connection=DriverManager.getConnection(url, username, password);
                PreparedStatement ps=connection.prepareStatement(searchSQL)) {
            ps.setInt(1, facultyId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                System.out.println();
                System.out.println("========== FACULTY FOUND ==========");

                System.out.println("Faculty ID: " + rs.getInt("facultyId"));
                System.out.println("Name: " + rs.getString("name"));
                System.out.println("Email: " + rs.getString("email"));
                System.out.println("Phone No: " + rs.getString("phoneNo"));
                System.out.println("Department: " + rs.getString("department"));
                System.out.println("Branch: " + rs.getString("branch"));
                System.out.println("Faculty Type: " + rs.getString("facultyType"));
                System.out.println("Designation: " + rs.getString("designation"));
                System.out.println("==================================");
            } else {
                System.out.println("No Faculty found with ID: " + facultyId);
            }

        } catch (SQLException e) {
            System.out.println("Search error: " + e.getMessage());
        }
    }


    @Override
    public void displayMember() {
        String query = "SELECT * FROM Faculty";

        try (Connection conn=DriverManager.getConnection(url, username, password);
                Statement stmt = conn.createStatement();
                ResultSet rs=stmt.executeQuery(query)) {
            ResultSetMetaData metaData=rs.getMetaData();
            int columnCount=metaData.getColumnCount();
            System.out.println();
            System.out.println("====================== ALL FACULTY ======================");

            for (int i = 1; i <= columnCount; i++) {
                System.out.print(metaData.getColumnName(i) + "\t\t");
            }
            System.out.println();

            while (rs.next()) {
                for (int i = 1; i <= columnCount; i++) {
                    System.out.print(rs.getString(i) + "\t\t");
                }
                System.out.println();
            }
            System.out.println("===========================================================");
        } catch (SQLException e) {
            System.out.println("Display error: " + e.getMessage());
        }
    }
}