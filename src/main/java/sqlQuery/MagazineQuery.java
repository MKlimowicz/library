package sqlQuery;

import db.Dbutil;
import model.Magazine;
import model.Publication;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class MagazineQuery {

    public Optional<Publication> addMagazine(Magazine magazine) {

        final String sqlQuery = "INSERT INTO magazine (title,publisher,language,monthDay,yearPub,isbn)VALUES(?,?,?,?,?,?)";
        int amountBooks = 0;
        try (
                Connection con = Dbutil.getInstance().getConnection();
                PreparedStatement preparedStatement = con.prepareStatement(sqlQuery)
        ) {

            preparedStatement.setString(1, magazine.getTitle());
            preparedStatement.setString(2, magazine.getPublisher());
            preparedStatement.setString(3, magazine.getLanguage());
            preparedStatement.setDate(4,magazine.getDate());
            preparedStatement.setInt(5, magazine.getYear().getValue());
            preparedStatement.setString(6, magazine.getIsbn());
            amountBooks = preparedStatement.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Błąd podczas dodawania magazynu.");
            e.printStackTrace();
        }

        return getBook(magazine, amountBooks);

    }


    public boolean checkIfItExists(String isbn){
        final String sql = "SELECT isbn FROM magazine WHERE isbn = ?";
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
                    "tym czy dany magazyn istnieje !!!");
        }
        return exists;
    }

    public List<Magazine> getAllMagazine() {
        List<Magazine>  magazines = new ArrayList<>();
        final String sqlQueary = "SELECT * FROM magazine";
        return findMagazinesByQuery(magazines, sqlQueary);
    }

    private List<Magazine> findMagazinesByQuery(List<Magazine> magazines, String sqlQueary) {
        try (
                Connection con = Dbutil.getInstance().getConnection();
                PreparedStatement preparedStatement = con.prepareStatement(sqlQueary);
        ) {
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {

                int id = resultSet.getInt("id");
                String title = resultSet.getString("title");
                String publisher = resultSet.getString("publisher");
                String language = resultSet.getString("language");
                Date date = resultSet.getDate("monthDay");
                int yearPub = resultSet.getInt("yearPub");
                String isbn = resultSet.getString("isbn");

                Magazine magazine = new Magazine(title,publisher,language,yearPub,date.getMonth(),date.getMonth(),isbn);

                magazine.setId(id);
                magazines.add(magazine);

            }

        } catch (SQLException e) {
            e.getSQLState();
        }

        return magazines;
    }

    private boolean positiveOrNot(int howMuch,int howMuchWas){
        boolean result = false;
        if (howMuch == howMuchWas)
            result = true;
        return result;
    }

    private Optional<Publication> getBook(Magazine magazine, int amountBooks) {
        Publication publication = magazine;
        if(positiveOrNot(1,amountBooks))
            return Optional.of(publication);
        else
            return Optional.empty();
    }

    public int deleteMagazineById(int id) {
        final String sqlQuery = "DELETE FROM MAGAZINE where id = ? ";
        int amountBooks = 0;
        try (
                Connection con = Dbutil.getInstance().getConnection();
                PreparedStatement preparedStatement = con.prepareStatement(sqlQuery)
        ) {

            preparedStatement.setInt(1, id);
            amountBooks = preparedStatement.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Błąd podczas usuwania magazynu.");
            e.printStackTrace();
        }

        return amountBooks;
    }

    public List<Magazine> findBookByTitle(String title) {
        List<Magazine> magazines = new ArrayList<>();
        final String sqlQueary = "SELECT * FROM magazine where title like '" + "%" +title+"%"+ "'";
        return findMagazinesByQuery(magazines, sqlQueary);
    }

    public List<Magazine> getAllMagazineNotBorrow() {
        List<Magazine> magazines = new ArrayList<>();
        final String sqlQueary = "SELECT * FROM magazine where id not in (select idBook from borrow)";
        return findMagazinesByQuery(magazines, sqlQueary);
    }
}
