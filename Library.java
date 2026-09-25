package LibraryManagementSystem;

import java.util.Scanner;

public class Library {

    public void addBook() {
        Book book=new Book();
        book.AddBook();
    }

    public void removeBook() {
        Book book=new Book();
        book.DeleteBook();
    }

    public void searchBook() {
        Book book=new Book();
        book.SearchBook();
    }

    public void registerMember() {
        Scanner scanner=new Scanner(System.in);
        System.out.println("\n========== REGISTER MEMBER ==========");
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

    public void issueBook() {
        IssueRecord issueRecord=new IssueRecord();
        issueRecord.issueBook();
    }

    public void returnBook() {
        IssueRecord issueRecord=new IssueRecord();
        issueRecord.returnBook();
    }

    public void calculateFine() {
        System.out.println("Fine calculation will be implemented.");
    }

    public void displayBooks() {
        Book book=new Book();
        book.DisplayBookInformation();
    }

    public void displayMembers() {
        Scanner scanner=new Scanner(System.in);

        System.out.println("\n========== DISPLAY MEMBERS ==========");
        System.out.println("1. Students");
        System.out.println("2. Faculty");

        System.out.print("Enter your choice: ");
        int choice=scanner.nextInt();

        if (choice==1) {
            Students student=new Students();
            student.displayMember();

        } else if (choice==2) {
            Faculty faculty=new Faculty();
            faculty.displayMember();
        } else {
            System.out.println("Invalid choice!");
        }
    }
}