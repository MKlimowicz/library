package services;

import exception.EmptyCollectionException;
import exception.PublicationAlreadyExistException;
import model.User;
import sqlQuery.UserQuery;

import java.sql.Date;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

public class UserServices {
        private UserQuery uQ = new UserQuery();

        public Optional<User> addUser(User user) {
            Optional<User> optionalUser;

            if (uQ.checkIfItExists(user.getLogin()))
                throw new PublicationAlreadyExistException(
                        "Taki użytkownik już istnieje w bazie");
            else
                optionalUser = uQ.addUser(user);

            return optionalUser;
        }

        public List<User> getAllUsers() {
            List<User> allUsers = uQ.getAllUsers();
            if (allUsers.size() == 0)
                throw new EmptyCollectionException("Nie ma żadnych użytkowników w bazie");
            else
                return allUsers;
        }

    public int deleteUser(int id) {
       return uQ.deleteUser(id);
    }

    public boolean checkIfThisDataIsCorrect(String login, String password) {
        return uQ.checkIfThisDataIsCorrect(login, password);
    }

    public String checkTheGroup(String login) {
            return uQ.checkTheGroup(login);
    }

    public int findUserByLogin(String login) {
            return uQ.findUserByLogin(login);
    }

    public int saveForASession(int id) {
        LocalDate now = LocalDate.now();
        java.sql.Date date = Date.valueOf(now);

        return  uQ.saveForASession(id,date);
    }

    public boolean logOutWithSession() {
        LocalDate now = LocalDate.now();
        java.sql.Date date = Date.valueOf(now);

        boolean execute = false;
        int idSession = uQ.findUserSession();

        if (idSession > 0)
            execute = uQ.logOutWithSession(idSession, date);

        return execute;


    }
}
