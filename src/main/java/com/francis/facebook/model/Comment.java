package com.francis.facebook.model;

public class Comment {
    private int id;
    private int user_id;
    private int post_id;
    private String body;

    public Comment(int id, int user_id, int post_id, String body) {
        this.id = id;
        this.user_id = user_id;
        this.post_id = post_id;
        this.body = body;
    }

    public Comment(int user_id, int post_id, String body) {
        this.user_id = user_id;
        this.post_id = post_id;
        this.body = body;
    }

    public int getId() {
        return id;
    }

    public int getUser_id() {
        return user_id;
    }

    public int getPost_id() {
        return post_id;
    }

    public String getBody() {
        return body;
    }
}
