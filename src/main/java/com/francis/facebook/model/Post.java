package com.francis.facebook.model;

import java.sql.*;


public class Post {
    private int user_id;
    private int id;
    private String body;
    private Timestamp createdAt;

    public Post(int id, int user_id, String body, Timestamp createdAt) {
        this.id = id;
        this.user_id = user_id;
        this.body = body;
        this.createdAt = createdAt;
    }

    public Post(int user_id, String body) {
        this.user_id = user_id;
        this.body = body;
    }
    public int getId() {
        return id;
    }
    public int getUser_id() {
        return user_id;
    }

    public String getBody() {
        return body;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }
}
