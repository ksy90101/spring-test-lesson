package com.example.springtestinglesson.post.dto;

public class TodoUpdateRequest {

    private final String title;
    private final String content;

    public TodoUpdateRequest(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }
}
