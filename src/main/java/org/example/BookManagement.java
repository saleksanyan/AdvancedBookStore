package org.example;
import java.sql.*;
import java.util.ArrayList;

/**
 * this class manages books by getting and updating some data from the database
 */
public class BookManagement {

    //updates books data in the database
    public static void updateBookDetails(int bookId, String newTitle, String newAuthor, String newGenre,
                                         double newPrice, int newQuantityInStock) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "UPDATE books SET title=?, author=?, genre=?," +
                    " Price=?, QuantityInStock=? WHERE BookID=?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, newTitle);
                preparedStatement.setString(2, newAuthor);
                preparedStatement.setString(3, newGenre);
                preparedStatement.setDouble(4, newPrice);
                preparedStatement.setInt(5, newQuantityInStock);
                preparedStatement.setInt(6, bookId);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //gives books list considering the data that the user gave
    public static void listBooksByGenreOrAuthor(String genreOrAuthor) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "SELECT * FROM Books WHERE Genre LIKE ? OR Author LIKE ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, "%" + genreOrAuthor + "%");
                preparedStatement.setString(2, "%" + genreOrAuthor + "%");

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        int bookID = resultSet.getInt("BookID");
                        String title = resultSet.getString("Title");
                        String author = resultSet.getString("Author");
                        String genre = resultSet.getString("Genre");
                        double price = resultSet.getDouble("Price");
                        int quantityInStock = resultSet.getInt("QuantityInStock");
                        System.out.println("------------------------------");
                        System.out.println("BookID: " + bookID);
                        System.out.println("Title: " + title);
                        System.out.println("Author: " + author);
                        System.out.println("Genre: " + genre);
                        System.out.println("Price: $" + price);
                        System.out.println("Quantity in Stock: " + quantityInStock);
                        System.out.println("------------------------------");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    //getting book id's
    public static ArrayList<String> getAllBookIDs() {
        ArrayList<String> bookIDs = new ArrayList<>();

        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "SELECT BookID FROM Books";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        String id = resultSet.getString("BookID");
                        bookIDs.add(id);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return bookIDs;
    }

    /**
     * getting books price considering their id
     * @param bookId books id
     * @return the price
     */
    public static double getBookPriceById(int bookId) {
        double price = -1.0;

        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "SELECT Price FROM Books WHERE BookID=?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, bookId);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        price = resultSet.getDouble("Price");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return price;
    }

    //returns the quantity of the books using their id's
    public static int getBookQuantityById(int bookId) {
        int quantity = -1;

        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "SELECT QuantityInStock FROM Books WHERE BookID=?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, bookId);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        quantity = resultSet.getInt("QuantityInStock");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return quantity;
    }
}

