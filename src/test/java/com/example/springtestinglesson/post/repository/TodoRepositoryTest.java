package com.example.springtestinglesson.post.repository;

import com.example.springtestinglesson.post.model.Todo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DataJpaTest
class TodoRepositoryTest {

    @Autowired
    private TodoRepository todoRepository;

    @DisplayName("저장 테스트")
    @Test
    void saveTest() {
        Todo todo = new Todo(null, "제목", "내용", "마르코");

        Todo savedTodo = todoRepository.save(todo);

        assertAll(
            () -> assertThat(savedTodo.getId()).isNotNull(),
            () -> assertThat(savedTodo.getTitle()).isEqualTo(todo.getTitle()),
            () -> assertThat(savedTodo.getContent()).isEqualTo(todo.getContent()),
            () -> assertThat(savedTodo.getUserName()).isEqualTo(todo.getUserName())
        );
    }

    @DisplayName("전체조회 테스트")
    @Test
    void findAllTest() {
        Todo todo = new Todo(null, "제목", "내용", "마르코");
        todoRepository.save(todo);

        List<Todo> todos = todoRepository.findAll();

        assertAll(
            () -> assertThat(todos).hasSize(1),
            () -> assertThat(todos.get(0).getId()).isNotNull(),
            () -> assertThat(todos.get(0).getTitle()).isEqualTo(todo.getTitle()),
            () -> assertThat(todos.get(0).getContent()).isEqualTo(todo.getContent()),
            () -> assertThat(todos.get(0).getUserName()).isEqualTo(todo.getUserName())
        );
    }

    @DisplayName("Id로 조회 테스트")
    @Test
    void findByIdTest() {
        Todo todo = new Todo(null, "제목", "내용", "마르코");
        Todo savedTodo = todoRepository.save(todo);

        Todo findTodo = todoRepository.findById(savedTodo.getId())
            .orElseThrow(() -> new IllegalArgumentException("해당 Todo가 존재하지 않습니다."));

        assertAll(
            () -> assertThat(findTodo.getId()).isNotNull(),
            () -> assertThat(findTodo.getTitle()).isEqualTo(todo.getTitle()),
            () -> assertThat(findTodo.getContent()).isEqualTo(todo.getContent()),
            () -> assertThat(findTodo.getUserName()).isEqualTo(todo.getUserName())
        );
    }

    @DisplayName("삭제 테스트")
    @Test
    void deleteByIdTest() {
        Todo todo = new Todo(null, "제목", "내용", "마르코");

        Todo savedTodo = todoRepository.save(todo);

        todoRepository.deleteById(savedTodo.getId());

        Optional<Todo> findTodo = todoRepository.findById(todo.getId());

        assertAll(
            () -> assertThat(findTodo).isNotPresent()
        );
    }
}
