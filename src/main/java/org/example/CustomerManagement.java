package org.example;
import java.sql.*;
import java.util.ArrayList;

public class CustomerManagement {
    public static void updateCustomerInformation(int customerId, String newName, String newEmail, String newPhone) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "UPDATE Customers SET Name=?, Email=?, Phone=? WHERE CustomerID=?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, newName);
                preparedStatement.setString(2, newEmail);
                preparedStatement.setString(3, newPhone);
                preparedStatement.setInt(4, customerId);
                preparedStatement.executeUpdate();
                System.out.println("Customer information updated successfully.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void viewCustomerPurchaseHistory(int customerId) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "SELECT Sales.DateOfSale, Books.Title, Sales.QuantitySold, Sales.TotalPrice " +
                    "FROM Sales " +
                    "JOIN Books ON Sales.BookID = Books.BookID " +
                    "WHERE Sales.CustomerID = ?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, customerId);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (!resultSet.isBeforeFirst()) {
                        System.out.println("No purchase history found for customer with ID: " + customerId);
                    } else {
                        System.out.println("Purchase history for customer with ID " + customerId + ":");
                        while (resultSet.next()) {
                            String dateOfSale = resultSet.getString("DateOfSale");
                            String bookTitle = resultSet.getString("Title");
                            int quantitySold = resultSet.getInt("QuantitySold");
                            double totalPrice = resultSet.getDouble("TotalPrice");

                            System.out.println("Date of Sale: " + dateOfSale);
                            System.out.println("Book Title: " + bookTitle);
                            System.out.println("Quantity Sold: " + quantitySold);
                            System.out.println("Total Price: $" + totalPrice);
                            System.out.println("------------------------------");
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<String> getAllCustomerEmails() {
        ArrayList<String> customerEmails = new ArrayList<>();

        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "SELECT Email FROM Customers";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        String email = resultSet.getString("Email");
                        customerEmails.add(email);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return customerEmails;
    }

    public static ArrayList<String> getAllCustomerIDs() {
            ArrayList<String> customerIDs = new ArrayList<>();

            try (Connection connection = DatabaseConnection.getConnection()) {
                String query = "SELECT CustomerID FROM Customers";

                try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                    try (ResultSet resultSet = preparedStatement.executeQuery()) {
                        while (resultSet.next()) {
                            String id = resultSet.getString("CustomerID");
                            customerIDs.add(id);
                        }
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return customerIDs;
    }
}


