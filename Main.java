package LibraryManagementSystem;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner=new Scanner(System.in);
        Library library=new Library();

        int choice;

        do {
            System.out.println("\n========================================");
            System.out.println("       LIBRARY MANAGEMENT SYSTEM");
            System.out.println("========================================");

            System.out.println("1. Add Book");
            System.out.println("2. Remove Book");
            System.out.println("3. Search Book");
            System.out.println("4. Display All Books");
            System.out.println("5. Register Member");
            System.out.println("6. Display Members");
            System.out.println("7. Issue Book");
            System.out.println("8. Return Book");
            System.out.println("9. Calculate Fine");
            System.out.println("10. Exit");

            System.out.print("\nEnter your choice: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    library.addBook();
                    break;

                case 2:
                    library.removeBook();
                    break;

                case 3:
                    library.searchBook();
                    break;

                case 4:
                    library.displayBooks();
                    break;

                case 5:
                    library.registerMember();
                    break;

                case 6:
                    library.displayMembers();
                    break;

                case 7:
                    library.issueBook();
                    break;

                case 8:
                    library.returnBook();
                    break;

                case 9:
                    library.calculateFine();
                    break;

                case 10:
                    System.out.println("\nThank you for using Library Management System!");
                    break;

                default:
                    System.out.println("Invalid choice! Please try again.");
            }

        } while (choice != 10);
        scanner.close();
    }
}