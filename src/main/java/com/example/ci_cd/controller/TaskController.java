package com.example.ci_cd.controller;

import com.example.ci_cd.dto.TaskRequestDto;
import com.example.ci_cd.dto.TaskResponseDto;
import com.example.ci_cd.mapper.TaskMapper;
import com.example.ci_cd.models.Task;
import com.example.ci_cd.service.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {
    private TaskService taskService;
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public ResponseEntity<List<TaskResponseDto>> findAll() {
        return ResponseEntity.ok(
                taskService.getTasks()
                        .stream()
                        .map(TaskMapper::map)
                        .toList()
        );
    }

    @PostMapping
    public ResponseEntity<TaskResponseDto> save(@RequestBody TaskRequestDto task) {
        Task task1 = taskService.createTask(task);

        return ResponseEntity.status(HttpStatus.CREATED).body(TaskMapper.map(task1));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskResponseDto> update(@PathVariable Long id, @RequestBody TaskRequestDto task) {
         return ResponseEntity.ok().body(TaskMapper.map(taskService.updateTask(id, task)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        taskService.deletTask(id);
        return ResponseEntity.noContent().build();
    }
}

