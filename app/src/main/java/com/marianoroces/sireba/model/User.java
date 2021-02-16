package com.marianoroces.sireba.model;

public class User {

    private String uid;
    private String email;
    private String password;
    private int id;

    public User(String uid, String email, String password, int id) {
        this.uid = uid;
        this.email = email;
        this.password = password;
        this.id = id;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
