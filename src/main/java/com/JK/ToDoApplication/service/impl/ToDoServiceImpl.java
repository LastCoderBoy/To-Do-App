package com.JK.ToDoApplication.service.impl;

import com.JK.ToDoApplication.models.ToDoTask;
import com.JK.ToDoApplication.repository.ToDoRepository;
import com.JK.ToDoApplication.service.ToDoService;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ToDoServiceImpl implements ToDoService {

    private final ToDoRepository toDoRepository;
    @Override
    public List<ToDoTask> getAllTasks() {
        return toDoRepository.findAll();
    }

    @Override
    public List<ToDoTask> getCompleteTasks() {
        return toDoRepository.findToDoTaskByIsComplete(true);
    }

    @Override
    public List<ToDoTask> getIncompleteTasks() {
        return toDoRepository.findToDoTaskByIsComplete(false);
    }

    @Override
    public void createTask(ToDoTask toDoTask) {
        toDoTask.setCreatedAt(Instant.now());
        toDoTask.setIsComplete(false);
        toDoRepository.save(toDoTask);
    }

    @Override
    public void deleteTask(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Task ID cannot be null");
        }
        try {
            toDoRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new IllegalStateException("Task not found with ID: " + id);
        }
    }


    @Override
    public void deleteAllTask() {
        try {
            toDoRepository.deleteAll();
        } catch (DataAccessException e) {
            throw new IllegalStateException("Failed to delete all tasks", e);
        }
    }


    @Override
    public ToDoTask updateTask(ToDoTask task, Long ID) {
        Optional<ToDoTask> taskToBeUpdatedOptional = toDoRepository.findById(ID);
        if(taskToBeUpdatedOptional.isPresent()){
            ToDoTask taskToBeUpdated = taskToBeUpdatedOptional.get();
            taskToBeUpdated.setDescription(task.getDescription());
            taskToBeUpdated.setIsComplete(true);
            taskToBeUpdated.setUpdatedAt(Instant.now()); // Update the timestamp
            return toDoRepository.save(taskToBeUpdated);
        } else {
            throw new RuntimeException("Task not found with ID: " + ID);
        }
    }
}
