package LibraryManagementSystem;

import java.sql.*;
import java.util.Scanner;

public class Book {

   static final String url="jdbc:mysql://localhost:3306/library_db";
    static final String username="root";
    static final String password="Shiva@165";


   private int bookId;
   private String title;
    private String author;
    private String category;
    private int totalQuantity;
    private int availableQuantity;

    public Book(){
        this.bookId=bookId;
        this.title=title;
        this.author=author;
        this.category=category;
        this.totalQuantity=totalQuantity;
        this.availableQuantity=availableQuantity;
    }

//    public Book(int bookId,String title,String author,String category,int totalQuantity,int availableQuantity){
//        this.bookId=bookId;
//        this.title=title;
//        this.author=author;
//        this.category=category;
//        this.totalQuantity=totalQuantity;
//        this.availableQuantity=availableQuantity;
//    }

    public void setBookId(int bookId){
        this.bookId=bookId;
    }

    public int getBookId() {
        return bookId;
    }

    public void StoreBookDetails(){

    }

    public void setTitle(String title){
        this.title=title;
    }

    public String getTitle() {
        return title;
    }

    public void setAuthor(String author){
        this.author=author;
    }

    public String getAuthor() {
        return author;
    }

    public void setCategory(String category){
        this.category=category;
    }

    public String getCategory() {
        return category;
    }

    public void setTotalQuantity(int totalQuantity){
        this.totalQuantity=totalQuantity;
    }

    public int getAvailableQuantity() {
        return availableQuantity;
    }

    public void setAvailableQuantity(int availableQuantity){
        this.availableQuantity=availableQuantity;
    }

    public int getTotalQuantity() {
        return totalQuantity;
    }

    public void AddBook(){
        try (Connection connection = DriverManager.getConnection(url,username,password)) {
            System.out.println("Connected to the Database!");
            Scanner scanner = new Scanner(System.in);

            System.out.print("Enter Book ID");
            int bookId = scanner.nextInt();
            scanner.nextLine();

            System.out.print("Enter Book title: ");
            String title = scanner.nextLine();

            System.out.print("Enter author: ");
            String author = scanner.nextLine();

            System.out.print("Enter Category: ");
            String category = scanner.nextLine();

            System.out.print("Enter totalQuantity: ");
            int totalQuantity = scanner.nextInt();


            int availableQuantity = totalQuantity;

            String insertSQL = "INSERT INTO Book (bookId, title, author, category, totalQuantity,availableQuantity) VALUES (?, ?, ?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {
                preparedStatement.setInt(1, bookId);
                preparedStatement.setString(2, title);
                preparedStatement.setString(3, author);
                preparedStatement.setString(4, category);
                preparedStatement.setInt(5, totalQuantity);
                preparedStatement.setInt(6, availableQuantity);

                int rowsInserted = preparedStatement.executeUpdate();
                if (rowsInserted > 0) {
                    System.out.println("Record Inserted successfully!");
                }
            } catch (SQLException e) {
                System.err.println("Insert error: " + e.getMessage());
            }
        } catch (SQLException e) {
            System.err.println("Database connection error: " + e.getMessage());
        }
    }

    public void UpdateBook() {

        try (
                Connection connection = DriverManager.getConnection(url, username, password);
                Scanner scanner = new Scanner(System.in)
        ) {

            System.out.print("Enter Book ID to update: ");
            int bookId = scanner.nextInt();
            scanner.nextLine();

            System.out.print("Enter new Book name: ");
            String title = scanner.nextLine();

            System.out.print("Enter new Author: ");
            String author = scanner.nextLine();

            System.out.print("Enter new Category: ");
            String category = scanner.nextLine();

            System.out.print("Enter new Total Quantity: ");
            int totalQuantity = scanner.nextInt();

            String updateSQL = "UPDATE Book SET title=?, author=?, category=?, totalQuantity=? WHERE bookId=?";

            try (PreparedStatement ps = connection.prepareStatement(updateSQL)) {

                ps.setString(1, title);
                ps.setString(2, author);
                ps.setString(3, category);
                ps.setInt(4, totalQuantity);
                ps.setInt(5, bookId);

                int rowsUpdated = ps.executeUpdate();

                if (rowsUpdated > 0) {
                    System.out.println("Book updated successfully!");
                } else {
                    System.out.println("No book found with Book ID: " + bookId);
                }

            } catch (SQLException e) {
                System.out.println("Update error: " + e.getMessage());
            }

        } catch (SQLException e) {
            System.out.println("Database connection error: " + e.getMessage());
        }
    }

    public void DeleteBook(){
        try (Connection connection = DriverManager.getConnection(url,username,password)){
            System.out.println("Connected to the Database!");

            Scanner scanner = new Scanner(System.in);

            System.out.print("Enter Book num/ID to delete: ");
            int bookId = scanner.nextInt();

            String deleteSQL = "Delete from Book WHERE bookId=?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(deleteSQL)) {
                preparedStatement.setInt(1, bookId);

                int rowsDeleted = preparedStatement.executeUpdate();

                if (rowsDeleted > 0) {
                    System.out.println("Record deleted successfully!!");
                } else {
                    System.out.println("No record found with book ID: " + bookId);
                }
            } catch(SQLException e) {
                System.err.println("Update error:" +e.getMessage());
            }
        } catch (SQLException e) {
            System.err.println("Datebase connection error: "+ e.getMessage());
        }

    }

    public void SearchBook() {

        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter Book ID to search: ");
        int bookId = scanner.nextInt();

        String searchSQL = "SELECT * FROM Book WHERE bookId=?";

        try (
                Connection connection = DriverManager.getConnection(
                        url, username, password);

                PreparedStatement ps = connection.prepareStatement(searchSQL)
        ) {

            ps.setInt(1, bookId);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                System.out.println("\n========== BOOK FOUND ==========");

                System.out.println("Book ID: " +
                        rs.getInt("bookId"));

                System.out.println("Title: " +
                        rs.getString("title"));

                System.out.println("Author: " +
                        rs.getString("author"));

                System.out.println("Category: " +
                        rs.getString("category"));

                System.out.println("Total Quantity: " +
                        rs.getInt("totalQuantity"));

                System.out.println("Available Quantity: " +
                        rs.getInt("availableQuantity"));

                System.out.println("================================");

            } else {

                System.out.println(
                        "No book found with Book ID: " + bookId
                );
            }

        } catch (SQLException e) {

            System.out.println(
                    "Search error: " + e.getMessage()
            );
        }
    }

    public void DisplayBookInformation() {
        String query = "SELECT * FROM Book";

        try (Connection conn = DriverManager.getConnection(url, username, password);
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(query)) {

            ResultSetMetaData metaData = rs.getMetaData();

            int columnCount = metaData.getColumnCount();

            System.out.println("Table: Book");

            // Print column names
            for (int i = 1; i <= columnCount; i++) {
                System.out.print(metaData.getColumnName(i) + "\t");
            }

            System.out.println();

            // Print rows
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
