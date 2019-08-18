package model;

import java.util.Objects;

public class User extends Person {
    private int id;
    private String login;
    private int password;
    private String group;

    public User(String firstName, String lastName, String pesel, String login, String group) {
        super(firstName, lastName, pesel);
        this.login = login;
        this.group = group;
    }


    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public int getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password.hashCode();
    }

    @Override
    public String toString() {
        return  super.toString() + "," + "id: " + id + ", " +
                "login='" + login + '\'';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        User user = (User) o;
        return Objects.equals(login, user.login);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), login);
    }
}
