package com.example.springtestinglesson.post.controller;

import com.example.springtestinglesson.post.dto.TodoCreateRequest;
import com.example.springtestinglesson.post.dto.TodoResponse;
import com.example.springtestinglesson.post.dto.TodoUpdateRequest;
import com.example.springtestinglesson.post.service.TodoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/todos")
public class TodoController {
    private final TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @GetMapping
    public ResponseEntity<List<TodoResponse>> findAll() {
        List<TodoResponse> todos = todoService.findAll();

        return ResponseEntity.ok(todos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TodoResponse> findById(@PathVariable Long id) {
        TodoResponse todo = todoService.findById(id);

        return ResponseEntity.ok(todo);
    }

    @PostMapping()
    public ResponseEntity<Void> create(@RequestBody TodoCreateRequest todoCreateRequest) throws URISyntaxException {
        Long id = todoService.create(todoCreateRequest);

        return ResponseEntity.created(new URI("/todos/" + id))
            .build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<TodoResponse> update(@RequestBody TodoUpdateRequest todoUpdateRequest, @PathVariable Long id) {
        TodoResponse todo = todoService.update(todoUpdateRequest, id);

        return ResponseEntity.ok(todo);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        todoService.delete(id);

        return ResponseEntity.noContent()
            .build();
    }
}
