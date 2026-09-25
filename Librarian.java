package LibraryManagementSystem;

import java.util.Scanner;

public class Librarian {
    private int librarianId;
    private String name;
    private String username;
    private String password;

    public Librarian() {
    }

    public Librarian(int librarianId, String name,
                     String username, String password) {

        this.librarianId=librarianId;
        this.name=name;
        this.username=username;
        this.password=password;
    }

    public int getLibrarianId() {
        return librarianId;
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public void addBooks() {
        Book book = new Book();
        book.AddBook();
    }

    public void removeBooks() {
        Book book=new Book();
        book.DeleteBook();
    }

    public void registerMembers() {
        Scanner scanner=new Scanner(System.in);
        System.out.println("\n==========REGISTER MEMBER==========");
        System.out.println("1. Student");
        System.out.println("2. Faculty");

        System.out.print("Enter your choice: ");
        int choice=scanner.nextInt();

        if (choice==1) {

            Students student=new Students();

            student.registerMember();

        } else if (choice==2) {

            Faculty faculty=new Faculty();

            faculty.registerMember();

        } else {
            System.out.println("Invalid choice!");
        }
    }

    public void issueBooks() {
        IssueRecord issueRecord=new IssueRecord();
        issueRecord.issueBook();
    }

    public void acceptReturnedBook() {
        IssueRecord issueRecord=new IssueRecord();
        issueRecord.returnBook();
    }

    public void viewRecords() {

        Scanner scanner=new Scanner(System.in);

        System.out.println("\n========== VIEW RECORDS ==========");
        System.out.println("1. Display Issued Books");
        System.out.println("2. Display Overdue Books");

        System.out.print("Enter your choice: ");
        int choice=scanner.nextInt();

        IssueRecord issueRecord=new IssueRecord();

        if (choice==1) {
            issueRecord.displayIssuedBooks();
        } else if (choice==2) {
            issueRecord.displayOverdueBooks();
        } else {
            System.out.println("Invalid choice!");
        }
    }

    public void renewBook() {
        IssueRecord issueRecord=new IssueRecord();
        issueRecord.renewBook();
    }
}