package com.JK.ToDoApplication.service.impl;

import com.JK.ToDoApplication.models.ToDoTask;
import com.JK.ToDoApplication.repository.ToDoRepository;
import com.JK.ToDoApplication.service.ToDoService;
import lombok.AllArgsConstructor;
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

        ToDoTask tempTodo = new ToDoTask();
        tempTodo.setCreatedAt(Instant.now());
        tempTodo.setIsComplete(false);
        tempTodo.setDescription(toDoTask.getDescription());

        toDoRepository.save(tempTodo);

    }

    @Override
    public void deleteTask(Long id) {
        toDoRepository.deleteById(id);
    }

    @Override
    public void deleteAllTask() {
        toDoRepository.deleteAll();
    }

    @Override
    public ToDoTask updateTask(ToDoTask task, Long ID) {
        Optional<ToDoTask> taskToBeUpdatedOptional = toDoRepository.findById(ID);
        if(taskToBeUpdatedOptional.isPresent()){
            ToDoTask taskToBeUpdated = taskToBeUpdatedOptional.get();
            taskToBeUpdated.setDescription(task.getDescription());
            taskToBeUpdated.setIsComplete(task.getIsComplete());
            taskToBeUpdated.setUpdatedAt(Instant.now()); // Update the timestamp
            return toDoRepository.save(taskToBeUpdated);
        } else {
            throw new RuntimeException("Task not found with ID: " + ID);
        }
    }
}