package com.JK.ToDoApplication.service;

import com.JK.ToDoApplication.models.ToDoTask;

import java.util.List;
import java.util.Optional;

public interface ToDoService {
    public List<ToDoTask> getAllTasks();
    public List<ToDoTask> getCompleteTasks();
    public List<ToDoTask> getIncompleteTasks();
    public void createTask(ToDoTask toDoTask);
    public void deleteTask(Long id);
    public void deleteAllTask();
    public ToDoTask updateTask(ToDoTask task, Long ID);

}
