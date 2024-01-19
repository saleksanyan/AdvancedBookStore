package org.example;

import java.util.Scanner;

import static org.example.BookManagement.getBookPriceById;
import static org.example.BookManagement.getBookQuantityById;
import static org.example.CustomerManagement.getAllCustomerEmails;
import static org.example.CustomerManagement.viewCustomerPurchaseHistory;
import static org.example.SalesProcessing.*;
import static org.example.SalesReports.*;

/**
 * this class creates user-friendly interface
 */
public class Console {
    public Console(){
        Scanner scanner = new Scanner(System.in);
        menu();
        String choice = scanner.nextLine().trim();
        while (!choice.equals("0")) {
            if(choice.equals("1")){
                updateBookDetails(scanner);
            }else if(choice.equals("2")){
                System.out.println("Enter the author or genre");
                String authorOrGenre = scanner.nextLine().trim();
                BookManagement.listBooksByGenreOrAuthor(authorOrGenre);
            }else if(choice.equals("3")){
                updateCustomerInformation(scanner);
            }else if(choice.equals("4")){
                System.out.println("Enter ID: ");
                String id = scanner.nextLine().trim();
                while(!CustomerManagement.getAllCustomerIDs().contains(id)){
                    System.out.println("Please enter valid ID");
                    id = scanner.nextLine().trim();

                }
                viewCustomerPurchaseHistory(Integer.parseInt(id));
            }else if(choice.equals("5")){
                processNewSale(scanner);
            }else if(choice.equals("6")){
                calculateTotalRevenue(scanner);
            }else if(choice.equals("7")){
                generateAllBooksSoldReport();
            }else if(choice.equals("8")){
                generateRevenueReportByGenre();
            }
            System.out.println();
            menu();
            choice = scanner.nextLine().trim();
        }
    }

    private static void calculateTotalRevenue(Scanner scanner) {
        System.out.println();
        System.out.println("Enter genre: ");
        String genre = scanner.nextLine().trim();
        while(!getAllGenres().contains(genre)){
            System.out.println("Please enter valid genre: ");
            genre = scanner.nextLine().trim();
        }
        System.out.println("Total revenue by genre is "+ calculateTotalRevenueByGenre(genre));
    }


    //processes new sales
    private static void processNewSale(Scanner scanner) {
        System.out.println("Enter book ID: ");
        String bookId = scanner.nextLine().trim();
        while(!BookManagement.getAllBookIDs().contains(bookId)){
            System.out.println("Please enter valid book ID: ");
            bookId = scanner.nextLine().trim();

        }
        System.out.println("Enter customer ID: ");
        String customerId = scanner.nextLine().trim();
        while(!CustomerManagement.getAllCustomerIDs().contains(customerId)){
            System.out.println("Please enter valid customer ID: ");
            customerId = scanner.nextLine().trim();
        }
        double price = getBookPriceById(Integer.parseInt(bookId));
        int quantity = getBookQuantityById(Integer.parseInt(bookId));
        SalesProcessing.processNewSale(Integer.parseInt(bookId),Integer.parseInt(customerId),price,quantity);
    }

    private static void updateCustomerInformation(Scanner scanner) {
        String regexMail = "^(.+)@(.+)$";
        String regexPhone = "^(\\+\\d{1,3}( )?)?((\\(\\d{1,3}\\))|\\d{1,3})[- .]?\\d{3,4}[- .]?\\d{4}$";
        System.out.println("Enter ID: ");
        String id = scanner.nextLine().trim();
        while(!CustomerManagement.getAllCustomerIDs().contains(id)){
            System.out.println("Please enter valid ID");
            id = scanner.nextLine().trim();

        }
        System.out.println("Enter customer name:");
        String name = scanner.nextLine().trim();
        System.out.println("Enter customer phone number:");
        String phone = scanner.nextLine().trim();
        while(!phone.matches(regexPhone)){
            System.out.println("Please enter valid phone number");
            phone = scanner.nextLine().trim();
        }
        System.out.println("Enter customer email:");
        String email = scanner.nextLine().trim();
        while(getAllCustomerEmails().contains(email) || !email.matches(regexMail)){
            System.out.println("Please enter valid mail");
            email = scanner.nextLine().trim();
        }
        CustomerManagement.updateCustomerInformation(Integer.parseInt(id),name,email,phone);
    }

    private static void updateBookDetails(Scanner scanner) {
        System.out.println("Enter ID: ");
        String id = scanner.nextLine().trim();
        while (!BookManagement.getAllBookIDs().contains(id)) {
            System.out.println("Please enter valid ID");
            id = scanner.nextLine().trim();

        }
        System.out.println("Enter title: ");
        String newTitle = scanner.nextLine().trim();
        System.out.println("Enter author: ");
        String newAuthor = scanner.nextLine().trim();
        System.out.println("Enter genre: ");
        String newGenre = scanner.nextLine().trim();
        System.out.println("Enter price: ");
        String newPrice = scanner.nextLine().trim();
        while (!newPrice.matches("\\d+") && !(Double.parseDouble(newPrice) >= 0)) {
            System.out.println("Please enter valid price");
            newPrice = scanner.nextLine().trim();

        }
        System.out.println("Enter quantity in stock: ");
        String newQuantityInStock = scanner.next().trim();
        while (!newQuantityInStock.matches("\\d+") && !(Integer.parseInt(newQuantityInStock) >= 0)) {
            System.out.println("Please enter valid price");
            newQuantityInStock = scanner.nextLine().trim();

        }
        BookManagement.updateBookDetails(Integer.parseInt(id), newTitle, newAuthor, newGenre, Double.parseDouble(newPrice),
                Integer.parseInt(newQuantityInStock));
    }

    //the menu
    private static void menu() {
        System.out.println("1. Update Book Details");
        System.out.println("2. List Books by Genre or Author");
        System.out.println("3. Update Customer Information");
        System.out.println("4. View Customer Purchase History");
        System.out.println("5. Process New Sale");
        System.out.println("6. Calculate Total Revenue by Genre");
        System.out.println("7. Generate All Books Sold Report");
        System.out.println("8. Generate Revenue by Genre Report");
        System.out.println("0. Exit");
        System.out.println();
        System.out.print("Enter your choice: ");

    }

}
