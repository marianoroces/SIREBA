package com.marianoroces.sireba.model;

public class User {

    private String uid;
    private String email;
    private String password;

    public User(String uid, String username, String password) {
        this.uid = uid;
        this.email = username;
        this.password = password;
    }

    public User() {

    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
