package LibraryManagementSystem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class EstablishConnection {
    static void main(String[] args) {
        try{
            Class.forName("com.mysql:cj.jdbc.Driver");
            System.out.println("class loaded successfully");
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        String url="jdbc:mysql://localhost:3306/library_db";
        String username="root";
        String password="Shiva@165";
        try{
            Connection con= DriverManager.getConnection(url,username,password);
            System.out.println("connection established");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
