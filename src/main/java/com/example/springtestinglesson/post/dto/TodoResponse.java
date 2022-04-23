package com.example.springtestinglesson.post.dto;

public class TodoResponse {
    private final Long id;
    private final String title;
    private final String content;
    private final String userName;

    public TodoResponse(Long id, String title, String content, String userName) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.userName = userName;
    }
}
