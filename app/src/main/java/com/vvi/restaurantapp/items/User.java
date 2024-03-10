package com.vvi.restaurantapp.items;

public class User {
    private final String fio;
    private final String login;
    private boolean isAdmin;

    public User(String fio, String login, boolean isAdmin) {
        this.fio = fio;
        this.login = login;
        this.isAdmin = isAdmin;
    }

    public String getFio() {
        return fio;
    }

    public String getLogin() {
        return login;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void changeMode() {
        isAdmin = !isAdmin;
    }
}