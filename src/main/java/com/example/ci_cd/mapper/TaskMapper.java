package com.example.ci_cd.mapper;

import com.example.ci_cd.dto.TaskResponseDto;
import com.example.ci_cd.models.Task;

import java.util.List;

public class TaskMapper {
    public static TaskResponseDto map(Task task) {
        return  new TaskResponseDto(
                task.getName(),
                task.getDescription(),
                task.getStatus()
        );
    }

}
