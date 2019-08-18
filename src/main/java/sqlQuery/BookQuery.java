package sqlQuery;

import db.Dbutil;
import exception.PublicationAlreadyExistException;
import model.Book;
import model.Publication;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BookQuery {

    public Optional<Publication> addBook(Book book) {

        final String sqlQuery = "INSERT INTO book (author,title,pages,yearPub,publisher,isbn)VALUES(?,?,?,?,?,?)";
        int amountBooks = 0;
        try (
                Connection con = Dbutil.getInstance().getConnection();
                PreparedStatement preparedStatement = con.prepareStatement(sqlQuery)
        ) {

            preparedStatement.setString(1, book.getAuthor());
            preparedStatement.setString(2, book.getTitle());
            preparedStatement.setInt(3, book.getPages());
            preparedStatement.setInt(4, book.getYear().getValue());
            preparedStatement.setString(5, book.getPublisher());
            preparedStatement.setString(6, book.getIsbn());
            amountBooks = preparedStatement.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Błąd podczas dodawania książki.");
            e.printStackTrace();
        }

        return getBook(book, amountBooks);

    }

    public boolean checkIfItExists(String isbn){
        final String sql = "SELECT isbn FROM book WHERE isbn = ?";
        boolean exists = false;
        try (
                Connection con = Dbutil.getInstance().getConnection();
                PreparedStatement preparedStatement = con.prepareStatement(sql);

        ) {
            preparedStatement.setString(1,isbn);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                exists = true;
            }

        } catch (SQLException e) {
            System.out.println(e.getSQLState() + "Błąd podczas pobierania z bazy informacji o " +
                    "tym czy dana książka istnieje !!!");
        }
        return exists;
    }

    private Optional<Publication> getBook(Book book, int amountBooks) {
        Publication publication = book;
        if(positiveOrNot(1,amountBooks))
            return Optional.of(book);
        else
            return Optional.empty();
    }

    private boolean positiveOrNot(int howMuch,int howMuchWas){
        boolean result = false;
        if (howMuch == howMuchWas)
            result = true;
        return result;
    }

    public List<Book> getAllBook() {
        List<Book> books = new ArrayList<>();
        final String sqlQueary = "SELECT * FROM book";
        return findBooksByQuery(books,sqlQueary);
    }

    public int deleteBookById(int id) {
        final String sqlQuery = "DELETE FROM BOOK where id = ? ";
        int amountBooks = 0;
        try (
                Connection con = Dbutil.getInstance().getConnection();
                PreparedStatement preparedStatement = con.prepareStatement(sqlQuery)
        ) {

            preparedStatement.setInt(1, id);
            amountBooks = preparedStatement.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Błąd podczas usuwania książki.");
            e.printStackTrace();
        }

        return amountBooks;
    }

    public List<Book> findBookByTitle(String title) {
        List<Book> books = new ArrayList<>();
        final String sqlQueary = "SELECT * FROM book where title like '" + "%" +title+"%"+ "'";
        return findBooksByQuery(books, sqlQueary);
    }

    private List<Book> findBooksByQuery(List<Book> books, String sqlQueary) {

        try (
                Connection con = Dbutil.getInstance().getConnection();
                PreparedStatement preparedStatement = con.prepareStatement(sqlQueary);
        ) {

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {

                int id = resultSet.getInt("id");
                String title = resultSet.getString("title");
                String name = resultSet.getString("author");
                int pages = resultSet.getInt("pages");
                int yearPub = resultSet.getInt("yearPub");
                String publisher = resultSet.getString("publisher");
                String isbn = resultSet.getString("isbn");

                Book book = new Book(title, name, yearPub, pages, publisher, isbn);

                book.setId(id);
                books.add(book);
            }

        } catch (SQLException e) {
            e.getSQLState();
        }

        return books;
    }

    public List<Book> getAllBookNotBorrow() {
        List<Book> books = new ArrayList<>();
        final  String sql = "SELECT * FROM book where id not in (select idBook from borrow)";
        return findBooksByQuery(books,sql);
    }
}
