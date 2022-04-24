package com.example.springtestinglesson.post.controller;

import com.example.springtestinglesson.post.dto.TodoCreateRequest;
import com.example.springtestinglesson.post.dto.TodoResponse;
import com.example.springtestinglesson.post.dto.TodoUpdateRequest;
import com.example.springtestinglesson.post.service.TodoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = TodoController.class)
class TodoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private TodoService todoService;

    @Test
    void findAllTest() throws Exception {
        TodoResponse todoResponse = new TodoResponse(1L, "제목", "내용", "마르코");

        given(todoService.findAll()).willReturn(List.of(todoResponse));

        mockMvc.perform(get("/todos"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    void findByIdTest() throws Exception {
        TodoResponse todoResponse = new TodoResponse(1L, "제목", "내용", "마르코");

        given(todoService.findById(anyLong())).willReturn(todoResponse);

        mockMvc.perform(get("/todos/1"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id", is(1)))
            .andExpect(jsonPath("$.title", is("제목")))
            .andExpect(jsonPath("$.content", is("내용")))
            .andExpect(jsonPath("$.userName", is("마르코")));
    }

    @Test
    void createTest() throws Exception {
        TodoCreateRequest todoCreateRequest = new TodoCreateRequest("제목", "내용", "마르코");

        given(todoService.create(any())).willReturn(1L);

        mockMvc.perform(post("/todos")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(todoCreateRequest)))
            .andExpect(status().isCreated())
            .andExpect(header().string("location", "todos/1"));
    }

    @Test
    void updateTest() throws Exception {
        TodoUpdateRequest todoUpdateRequest = new TodoUpdateRequest("제목 수정", "내용 수정");
        TodoResponse todoResponse = new TodoResponse(1L, "제목", "내용", "마르코");

        given(todoService.update(any(), anyLong())).willReturn(todoResponse);

        mockMvc.perform(put("/todos/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(todoUpdateRequest)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id", is(1)))
            .andExpect(jsonPath("$.title", is("제목")))
            .andExpect(jsonPath("$.content", is("내용")))
            .andExpect(jsonPath("$.userName", is("마르코")));
    }

    @Test
    void deleteTest() throws Exception {
        mockMvc.perform(delete("/todos/1"))
            .andExpect(status().isNoContent());
    }
}
