package com.example.ci_cd.service;

import com.example.ci_cd.TaskRepository;
import com.example.ci_cd.dto.TaskRequestDto;
import com.example.ci_cd.models.Task;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;

    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public List<Task> getTasks(){
        return taskRepository.findAll();
    }

    @Override
    public Task createTask(TaskRequestDto task){
        Task newTask = new Task();
        newTask.setName(task.name());
        newTask.setDescription(task.description());
        newTask.setStatus(task.status());
        return taskRepository.save(newTask);
    }

    @Override
    public Task updateTask(Long id, TaskRequestDto request){
        Optional<Task> task = taskRepository.findById(id);

        if(task.isPresent()){
            Task newTask = task.get();
            newTask.setName(request.name());
            newTask.setDescription(request.description());
            newTask.setStatus(request.status());
            taskRepository.save(newTask);
            return newTask;
        }else {
            throw new RuntimeException("Task with id " + id + " not found");
        }
    }

    @Override
    public void deletTask(Long id){
        Optional<Task> task = taskRepository.findById(id);
        if(task.isPresent()){
            taskRepository.delete(task.get());
        }else  {
            throw new RuntimeException("Task with id " + id + " not found");
        }
    }
}
