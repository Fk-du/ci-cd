package com.example.ci_cd.controller;


import com.example.ci_cd.dto.TaskRequestDto;
import com.example.ci_cd.models.Task;
import com.example.ci_cd.service.TaskService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TaskController.class)
class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private TaskService taskService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldCreateTask() throws Exception {
        TaskRequestDto request =
                new TaskRequestDto("New Task", "New Desc", "OPEN");

        Task savedTask =
                new Task(1L, "New Task", "New Desc", "OPEN");

        when(taskService.createTask(any(TaskRequestDto.class)))
                .thenReturn(savedTask);

        mockMvc.perform(post("/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("New Task"))
                .andExpect(jsonPath("$.status").value("OPEN"));
    }

    @Test
    void shouldUpdateTask() throws Exception {
        TaskRequestDto request =
                new TaskRequestDto("Updated", "Updated Desc", "DONE");

        Task updatedTask =
                new Task(1L, "Updated", "Updated Desc", "DONE");

        when(taskService.updateTask(eq(1L), any(TaskRequestDto.class)))
                .thenReturn(updatedTask);

        mockMvc.perform(put("/tasks/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("DONE"));
    }

    @Test
    void shouldDeleteTask() throws Exception {
        doNothing().when(taskService).deletTask(1L);

        mockMvc.perform(delete("/tasks/{id}", 1L))
                .andExpect(status().isNoContent());
    }

    @Test
    void shouldReturnAllTasks() throws Exception {
        List<Task> tasks = List.of(
                new Task(1L, "Task 1", "Desc 1", "OPEN"),
                new Task(2L, "Task 2", "Desc 2", "DONE")
        );

        when(taskService.getTasks()).thenReturn(tasks);

        mockMvc.perform(get("/tasks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Task 1"))
                .andExpect(jsonPath("$[1].status").value("DONE"));
    }


}
