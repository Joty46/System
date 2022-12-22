package com.example.kutibari;

public class User {
    String mail,username,password,role,uid;

    public User(String mail, String username, String password, String role, String uid) {
//        this.phone = phone;
        this.mail = mail;
        this.username = username;
        this.password = password;
        this.role = role;
        this.uid = uid;
    }

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

//    public String getPhone() {
//        return phone;
//    }

    public String getMail() { return mail; }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }

//    public void setPhone(String phone) {
//        this.phone = phone;
//    }

    public void setMail(String mail) { this.mail = mail; }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
