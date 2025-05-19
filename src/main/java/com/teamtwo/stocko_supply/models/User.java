package com.teamtwo.stocko_supply.models;

public class User {

    private String username;
    private String password;
    private String role;

    public User() {
    }

    public User(String nameInput, String passwd, String roleInput) {
        this.username = nameInput;
        this.password = passwd;
        this.role = roleInput;
    }

    public void setUsername(String nama) {
        this.username = nama;
    }

    public String getUsername() {
        return username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}
