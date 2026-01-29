package com.example.ci_cd.service;

import com.example.ci_cd.dto.TaskRequestDto;
import com.example.ci_cd.dto.TaskResponseDto;
import com.example.ci_cd.models.Task;

import java.util.List;

public interface TaskService {
    List<Task> getTasks();

    Task createTask(TaskRequestDto task);

    Task updateTask(Long id, TaskRequestDto request);

    void deletTask(Long id);
}
