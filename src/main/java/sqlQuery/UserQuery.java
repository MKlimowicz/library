package sqlQuery;

import db.Dbutil;
import model.User;

import java.sql.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

public class UserQuery {

    public Optional<User> addUser(User user){
        final String sqlQuery = "INSERT INTO user (firstName,lastName,pesel,password,login,grouplib)VALUES(?,?,?,?,?,?)";
        int amountBooks = 0;
        try (
                Connection con = Dbutil.getInstance().getConnection();
                PreparedStatement preparedStatement = con.prepareStatement(sqlQuery);
        ) {

            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setString(2, user.getLastName());
            preparedStatement.setString(3, user.getPesel());
            preparedStatement.setInt(4, user.getPassword());
            preparedStatement.setString(5, user.getLogin());
            preparedStatement.setString(6,user.getGroup());
            amountBooks = preparedStatement.executeUpdate();

        } catch (
                SQLException e) {
            System.out.println("Błąd podczas dodawania użytkownika do bazy.");
            e.printStackTrace();
        }

        return getUser(user,amountBooks);

    }


    public boolean checkIfItExists(String login){
        final String sql = "SELECT * FROM  user WHERE login = " + login;
        return checkIfTheAccountExists(sql);
    }

    private boolean checkIfTheAccountExists(String sql) {
        boolean exists = false;
        try (
                Connection con = Dbutil.getInstance().getConnection();
                PreparedStatement preparedStatement = con.prepareStatement(sql);

        ) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                exists = true;
            }

        } catch (SQLException e) {
            System.out.println("Dany użytkownik nie istnieje w bazie");
        }
        return exists;
    }

    private Optional<User> getUser(User user,int amountUser) {
        if(positiveOrNot(1,amountUser))
            return Optional.of(user);
        else
            return Optional.empty();
    }

    private boolean positiveOrNot(int howMuch,int howMuchWas){
        boolean result = false;
        if (howMuch == howMuchWas)
            result = true;
        return result;
    }

    public List<User> getAllUsers() {
        List<User>  users = new ArrayList<>();

        final String sqlQueary = "SELECT * FROM user";

        try (
                Connection con = Dbutil.getInstance().getConnection();
                PreparedStatement preparedStatement = con.prepareStatement(sqlQueary);
        ) {
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {

                int id = resultSet.getInt("id");
                String firstName = resultSet.getString("firstName");
                String lastName = resultSet.getString("lastName");
                String pesel = resultSet.getString("pesel");
                String login = resultSet.getString("login");
                String group = resultSet.getString("group");

                User user = new User(firstName,lastName,pesel,login,group);

                user.setId(id);
                users.add(user);

            }

        } catch (SQLException e) {
            e.getSQLState();
        }

        return users;

    }

    public int deleteUser(int id) {
        final String sqlQuery = "DELETE FROM user where id = ? ";
        int amountUser = 0;
        try (
                Connection con = Dbutil.getInstance().getConnection();
                PreparedStatement preparedStatement = con.prepareStatement(sqlQuery)
        ) {

            preparedStatement.setInt(1, id);
            amountUser = preparedStatement.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Błąd podczas usuwania użytkownika.");
            e.printStackTrace();
        }

        return amountUser;
    }

    public boolean checkIfThisDataIsCorrect(String login, String password) {
        int hashPassword = password.hashCode();
        String sql = "select * from user where login = ? and password = ?";
        boolean exists = false;
        try (
                Connection con = Dbutil.getInstance().getConnection();
                PreparedStatement preparedStatement = con.prepareStatement(sql);

        ) {
            preparedStatement.setString(1,login);
            preparedStatement.setInt(2,hashPassword);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                exists = true;
            }

        } catch (SQLException e) {
            System.out.println("Dany użytkownik nie istnieje w bazie");
        }
        return exists;
    }

    public String checkTheGroup(String login) {
        String sql = "SELECT * FROM user WHERE login = ?";
        String group = "";
        try (
                Connection con = Dbutil.getInstance().getConnection();
                PreparedStatement preparedStatement = con.prepareStatement(sql);

        ) {
            preparedStatement.setString(1,login);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                group = resultSet.getString("grouplib");
            }

        } catch (SQLException e) {
            System.out.println(e.getSQLState() + "Błąd podczas pobierania z bazy informacji o " +
                    "tym czy dany użytkownik istnieje !!!");
        }
        return group;
    }

    public int findUserByLogin(String login) {
        String sql = "SELECT * FROM user WHERE login = ?";
        int id = 0;
        try (
                Connection con = Dbutil.getInstance().getConnection();
                PreparedStatement preparedStatement = con.prepareStatement(sql);

        ) {
            preparedStatement.setString(1,login);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {

                id = resultSet.getInt("id");
            }

        } catch (SQLException e) {
            System.out.println("Błąd podczas pobierania uzytkownika z bazy na podstawie loginu");
        }
        return id;
    }

    public int saveForASession(int id, Date date) {
        final String sqlQuery = "INSERT INTO session (idUser,dataStart,dataEnd,online)VALUES(?,?,?,?)";
        int numerAdded = 0;
        try (
                Connection con = Dbutil.getInstance().getConnection();
                PreparedStatement preparedStatement = con.prepareStatement(sqlQuery);
        ) {

            preparedStatement.setInt(1, id);
            preparedStatement.setDate(2,date);
            preparedStatement.setDate(3, null);
            preparedStatement.setBoolean(4, true);

            numerAdded = preparedStatement.executeUpdate();

        } catch (
                SQLException e) {
            System.out.println("Błąd podczas dodawania sesji do bazy do bazy.");
            e.printStackTrace();
        }

        return numerAdded;

    }

    public int findUserSession() {
        String sql = "SELECT * FROM session WHERE online = true";
        int id = 0;
        try (
                Connection con = Dbutil.getInstance().getConnection();
                PreparedStatement preparedStatement = con.prepareStatement(sql);
        ) {

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                id = resultSet.getInt("id");
            }

        } catch (SQLException e) {
            System.out.println("Błąd podczas pobierania sesji z bazy ");
        }
        return id;
    }

    public boolean logOutWithSession(int idSession, Date date) {
        String sql  = "UPDATE session SET dataEnd = ?,online = false WHERE id = ?  ";

        int i = 0;
        try (
                Connection con = Dbutil.getInstance().getConnection();
                PreparedStatement preparedStatement = con.prepareStatement(sql);
        ) {

            preparedStatement.setDate(1,date);
            preparedStatement.setInt(2,idSession);
            i = preparedStatement.executeUpdate();


        } catch (SQLException e) {
            System.out.println("Błąd podczas zamykania sesji w bazie ");
        }

        return i == 1;

    }
}
