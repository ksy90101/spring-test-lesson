package com.example.springtestinglesson.post.controller;

import com.example.springtestinglesson.post.service.TodoService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PostController {
    private final TodoService postService;

    public PostController(TodoService postService) {
        this.postService = postService;
    }


}
