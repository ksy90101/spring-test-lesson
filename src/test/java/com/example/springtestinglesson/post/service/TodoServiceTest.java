package com.example.springtestinglesson.post.service;

import com.example.springtestinglesson.post.dto.TodoCreateRequest;
import com.example.springtestinglesson.post.dto.TodoResponse;
import com.example.springtestinglesson.post.dto.TodoUpdateRequest;
import com.example.springtestinglesson.post.model.Todo;
import com.example.springtestinglesson.post.repository.TodoRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DataJpaTest
@Import(TodoService.class)
class TodoServiceTest {

    @Autowired
    private TodoService todoService;

    @Autowired
    private TodoRepository todoRepository;

    @Test
    void createTest() {
        TodoCreateRequest todoCreateRequest = new TodoCreateRequest("제목", "내용", "마르코");
        Long id = todoService.create(todoCreateRequest);

        Optional<Todo> todo = todoRepository.findById(id);

        assertAll(
            () -> assertThat(todo).isPresent()
        );
    }

    @Test
    void findAllTest() {
        Todo todo = new Todo(null, "제목", "내용", "마르코");
        todoRepository.save(todo);

        List<TodoResponse> todoResponses = todoService.findAll();

        assertAll(
            () -> assertThat(todoResponses).hasSize(1),
            () -> assertThat(todoResponses.get(0).getId()).isEqualTo(todo.getId()),
            () -> assertThat(todoResponses.get(0).getTitle()).isEqualTo(todo.getTitle()),
            () -> assertThat(todoResponses.get(0).getContent()).isEqualTo(todo.getContent()),
            () -> assertThat(todoResponses.get(0).getUserName()).isEqualTo(todo.getUserName())
        );
    }

    @Test
    void findByIdTest() {
        Todo todo = new Todo(null, "제목", "내용", "마르코");
        Todo savedTodo = todoRepository.save(todo);

        TodoResponse todoResponse = todoService.findById(savedTodo.getId());

        assertAll(
            () -> assertThat(todoResponse.getId()).isEqualTo(todo.getId()),
            () -> assertThat(todoResponse.getTitle()).isEqualTo(todo.getTitle()),
            () -> assertThat(todoResponse.getContent()).isEqualTo(todo.getContent()),
            () -> assertThat(todoResponse.getUserName()).isEqualTo(todo.getUserName())
        );
    }

    @Test
    void updateTest() {
        Todo todo = new Todo(null, "제목", "내용", "마르코");
        Todo savedTodo = todoRepository.save(todo);

        TodoUpdateRequest todoUpdateRequest = new TodoUpdateRequest("제목 수정", "내용 수정");
        TodoResponse todoResponse = todoService.update(todoUpdateRequest, savedTodo.getId());

        assertAll(
            () -> assertThat(todoResponse.getId()).isEqualTo(todo.getId()),
            () -> assertThat(todoResponse.getTitle()).isEqualTo(todoUpdateRequest.getTitle()),
            () -> assertThat(todoResponse.getContent()).isEqualTo(todoUpdateRequest.getContent()),
            () -> assertThat(todoResponse.getUserName()).isEqualTo(todo.getUserName())
        );
    }

    @Test
    void deleteTest() {
        Todo todo = new Todo(null, "제목", "내용", "마르코");
        Todo savedTodo = todoRepository.save(todo);

        todoService.delete(savedTodo.getId());

        Optional<Todo> findTodo = todoRepository.findById(savedTodo.getId());

        assertAll(
            () -> assertThat(findTodo).isNotPresent()
        );
    }
}
