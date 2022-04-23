package com.example.springtestinglesson.post.service;

import com.example.springtestinglesson.post.dto.TodoCreateRequest;
import com.example.springtestinglesson.post.dto.TodoResponse;
import com.example.springtestinglesson.post.dto.TodoUpdateRequest;
import com.example.springtestinglesson.post.model.Todo;
import com.example.springtestinglesson.post.repository.TodoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TodoService {

    private final TodoRepository todoRepository;

    public TodoService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    public Long create(TodoCreateRequest todoCreateRequest) {
        Todo todo = new Todo(null, todoCreateRequest.getTitle(), todoCreateRequest.getContent(), todoCreateRequest.getUserName());

        Todo savedTodo = todoRepository.save(todo);

        return savedTodo.getId();
    }


    public List<TodoResponse> findAll() {
        List<Todo> todos = todoRepository.findAll();

        return todos.stream()
            .map(todo -> new TodoResponse(todo.getId(), todo.getTitle(), todo.getContent(), todo.getUserName()))
            .collect(Collectors.toUnmodifiableList());
    }

    public TodoResponse findById(Long id) {
        Todo todo = todoRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("해당 Todo를 찾을 수 없습니다. id : " + id));

        return new TodoResponse(todo.getId(), todo.getTitle(), todo.getContent(), todo.getUserName());
    }

    public TodoResponse update(TodoUpdateRequest todoUpdateRequest, Long id) {
        Todo todo = todoRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("해당 Todo를 찾을 수 없습니다. id : " + id));

        todo.update(todoUpdateRequest.getTitle(), todoUpdateRequest.getContent());

        return new TodoResponse(todo.getId(), todo.getTitle(), todo.getContent(), todo.getUserName());
    }

    public void delete(Long id) {
        todoRepository.deleteById(id);
    }
}
