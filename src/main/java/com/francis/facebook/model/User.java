package com.francis.facebook.model;
import java.sql.*;
import java.text.SimpleDateFormat;

public class User {
    private int id;
    private String firstName;
    private String lastName;
    private String email;

    private String password;
    private String gender;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    public User(int id, String firstName, String lastName, String email, String gender, Timestamp createdAt) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.gender = gender;
        this.createdAt = createdAt;
    }

    public User(String firstName, String lastName, String email, String password, String gender) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.gender = gender;
    }

    public User(int id, String firstName, String lastName, String email, String gender) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email; this.gender = gender;
    }

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getGender() {
        return gender;
    }

    public String getName() {
        return this.getFirstName() + " " + this.getLastName();
    }

    public Timestamp getCreatedAt() {

        return createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }
}
