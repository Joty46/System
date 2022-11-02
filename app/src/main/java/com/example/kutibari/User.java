package com.example.kutibari;

public class User {
    String phone,username,password;

    public User(String phone, String username, String password) {
        this.phone = phone;
        this.username = username;
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
