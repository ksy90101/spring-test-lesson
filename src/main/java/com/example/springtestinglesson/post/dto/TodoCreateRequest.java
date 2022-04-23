package com.example.springtestinglesson.post.dto;

public class TodoCreateRequest {
    private final String title;
    private final String content;
    private final String userName;

    public TodoCreateRequest(String title, String content, String userName) {
        this.title = title;
        this.content = content;
        this.userName = userName;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getUserName() {
        return userName;
    }
}
