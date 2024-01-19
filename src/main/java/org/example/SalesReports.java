package org.example;
import java.sql.*;

/**
 * this class generates reports about books
 */
public class SalesReports {

    //gives the list of books that are sold
    public static void generateAllBooksSoldReport() {
        try (Connection connection = DatabaseConnection.getConnection();
             Statement statement = connection.createStatement()) {
            String query = "SELECT books.Title, customers.Name, sales.DateOfSale " +
                    "FROM sales " +
                    "JOIN books ON sales.BookID = books.BookID " +
                    "JOIN customers ON sales.CustomerID = customers.CustomerID";
            try (ResultSet resultSet = statement.executeQuery(query)) {
                while (resultSet.next()) {
                    String title = resultSet.getString("Title");
                    String customerName = resultSet.getString("Name");
                    Date dateOfSale = resultSet.getDate("DateOfSale");
                    System.out.println("Title: " + title + ", Customer Name: " + customerName + ", Date of Sale: " + dateOfSale);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /**
     * gives revenue report considering genre of the book
     */
    public static void generateRevenueReportByGenre() {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String genreQuery = "SELECT DISTINCT Genre FROM Books";
            try (PreparedStatement genreStatement = connection.prepareStatement(genreQuery)) {
                try (ResultSet genreResultSet = genreStatement.executeQuery()) {
                    while (genreResultSet.next()) {
                        String genre = genreResultSet.getString("Genre");
                        double totalRevenue = calculateTotalRevenueByGenre(genre);
                        System.out.println("Total revenue for genre " + genre + ": $" + totalRevenue);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * calculates total revenue of the book with the given genre
     * @param genre books genre
     * @return total revenue
     */
    public static double calculateTotalRevenueByGenre(String genre) {
        double totalRevenue = 0.0;

        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "SELECT SUM(Sales.TotalPrice) AS TotalRevenue " +
                    "FROM Sales " +
                    "JOIN Books ON Sales.BookID = Books.BookID " +
                    "WHERE Books.Genre LIKE ?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, "%" + genre + "%");

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        totalRevenue = resultSet.getDouble("TotalRevenue");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return totalRevenue;
    }
}

