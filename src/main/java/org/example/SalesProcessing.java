package org.example;

import java.sql.*;
import java.util.ArrayList;

/**
 * this class indicates the sale process
 */
public class SalesProcessing {

    /**
     * builds a module to handle new sales, ensuring stock quantities and sales data are updated correctly.
     * @param bookId book's id
     * @param customerId customer's id
     * @param price book's price
     * @param quantity book's quantity
     */
    public static void processNewSale(int bookId, int customerId, double price, int quantity) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            connection.setAutoCommit(false);

            // Update stock quantity
            String updateStockQuery = "UPDATE books SET QuantityInStock = QuantityInStock - ? WHERE BookID = ?";
            try (PreparedStatement updateStockStatement = connection.prepareStatement(updateStockQuery)) {
                updateStockStatement.setInt(1, quantity);
                updateStockStatement.setInt(2, bookId);
                updateStockStatement.executeUpdate();
            }

            // Insert sales data
            String insertSaleQuery = "INSERT INTO sales(BookID, CustomerID, TotalPrice, QuantitySold) VALUES (?, ?, ?, ?)";
            try (PreparedStatement insertSaleStatement = connection.prepareStatement(insertSaleQuery)) {
                insertSaleStatement.setInt(1, bookId);
                insertSaleStatement.setInt(2, customerId);
                insertSaleStatement.setDouble(3, price);
                insertSaleStatement.setInt(4, quantity);
                insertSaleStatement.executeUpdate();
            }

            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    //getting all the genres from the database
    public static ArrayList<String> getAllGenres() {
        ArrayList<String> genres = new ArrayList<>();

        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "SELECT DISTINCT Genre FROM Books";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        String genre = resultSet.getString("Genre");
                        genres.add(genre);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return genres;
    }


}
