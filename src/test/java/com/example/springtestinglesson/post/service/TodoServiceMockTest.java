package com.example.springtestinglesson.post.service;

import com.example.springtestinglesson.post.dto.TodoCreateRequest;
import com.example.springtestinglesson.post.dto.TodoResponse;
import com.example.springtestinglesson.post.dto.TodoUpdateRequest;
import com.example.springtestinglesson.post.model.Todo;
import com.example.springtestinglesson.post.repository.TodoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class TodoServiceMockTest {

    @InjectMocks
    private TodoService todoService;

    @Mock
    private TodoRepository todoRepository;

    @Test
    void createTest() {
        Todo todo = new Todo(1L, "제목", "내용", "마르코");
        TodoCreateRequest todoCreateRequest = new TodoCreateRequest("제목", "내용", "마르코");
        given(todoRepository.save(any())).willReturn(todo);

        Long id = todoService.create(todoCreateRequest);

        assertAll(
            () -> assertThat(id).isEqualTo(1L)
        );
    }

    @Test
    void findAllTest() {
        Todo todo = new Todo(1L, "제목", "내용", "마르코");
        List<Todo> todos = List.of(todo);

        given(todoRepository.findAll()).willReturn(todos);

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
        Todo todo = new Todo(1L, "제목", "내용", "마르코");

        given(todoRepository.findById(anyLong())).willReturn(Optional.of(todo));

        TodoResponse todoResponse = todoService.findById(1L);

        assertAll(
            () -> assertThat(todoResponse.getId()).isEqualTo(todo.getId()),
            () -> assertThat(todoResponse.getTitle()).isEqualTo(todo.getTitle()),
            () -> assertThat(todoResponse.getContent()).isEqualTo(todo.getContent()),
            () -> assertThat(todoResponse.getUserName()).isEqualTo(todo.getUserName())
        );
    }

    @Test
    void updateTest() {
        Todo todo = new Todo(1L, "제목", "내용", "마르코");

        given(todoRepository.findById(anyLong())).willReturn(Optional.of(todo));

        TodoUpdateRequest todoUpdateRequest = new TodoUpdateRequest("제목 수정", "내용 수정");
        TodoResponse todoResponse = todoService.update(todoUpdateRequest, 1L);

        assertAll(
            () -> assertThat(todoResponse.getId()).isEqualTo(todo.getId()),
            () -> assertThat(todoResponse.getTitle()).isEqualTo(todoUpdateRequest.getTitle()),
            () -> assertThat(todoResponse.getContent()).isEqualTo(todoUpdateRequest.getContent()),
            () -> assertThat(todoResponse.getUserName()).isEqualTo(todo.getUserName())
        );

    }

    @Test
    void deleteTest() {
        todoService.delete(1L);

        verify(todoRepository, times(1)).deleteById(anyLong());
    }
}
