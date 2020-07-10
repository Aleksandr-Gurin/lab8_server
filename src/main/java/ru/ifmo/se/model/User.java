package ru.ifmo.se.model;

import java.io.Serializable;

public class User implements Serializable {
    private int id;
    private  String login;
    private String password;
    private Status status;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return login.equals(user.getLogin()) && password.equals(user.getPassword());
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + login.hashCode();
        result = 31 * result + password.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", status=" + status +
                '}';
    }

    public int getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }
    public String getPassword() {
        return password;
    }

    public void setId(int id) {
        this.id = id;
    }
    public void setLogin(String login) {
        this.login = login;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public enum Status{
        UNREGISTER,
        LOGIN,
        UNLOGIN;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
