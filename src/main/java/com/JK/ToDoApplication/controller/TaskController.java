package com.JK.ToDoApplication.controller;

import com.JK.ToDoApplication.models.ToDoTask;
import com.JK.ToDoApplication.service.impl.ToDoServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("api/v1/hometask")
public class TaskController {

    private final ToDoServiceImpl toDoService;

    @GetMapping("/complete")
    public String getCompleteTasks(Model model) {
        List<ToDoTask> completeTasks = toDoService.getCompleteTasks();
        model.addAttribute("tasks", completeTasks);
        model.addAttribute("taskType", "Complete");
        return "index";
    }

    @GetMapping("/incomplete")
    public String getIncompleteTasks(Model model) {
        List<ToDoTask> incompleteTasks = toDoService.getIncompleteTasks();
        model.addAttribute("tasks", incompleteTasks);
        model.addAttribute("taskType", "Incomplete");
        return "index";
    }

    @PostMapping("/addTask")
    public String createTask(@ModelAttribute("task") ToDoTask task, Model model) {
        ToDoTask tempTask = new ToDoTask();
        tempTask.setDescription(task.getDescription());
        if (task.getDescription() == null || task.getDescription().isEmpty()) {
            model.addAttribute("errorMessage", "Description is required");
            return "index"; // Return to the form if the description is missing
        }
        toDoService.createTask(tempTask);
        return "redirect:/";
    }

    @DeleteMapping(path = "/del/{taskID}")
    public String deleteTask (@PathVariable("taskID") Long studentID){
        toDoService.deleteTask(studentID);
        return "redirect:/";
    }

    @DeleteMapping(path = "/del/all")
    public String deleteAllTasks(){
        toDoService.deleteAllTask();
        return "redirect:/";
    }

    @PutMapping("/tasks/{id}")
    public String updateTask(@PathVariable Long id,
                                @ModelAttribute("ToDoTask") ToDoTask task,
                                Model model) {
        try {
            toDoService.updateTask(task, id);
            return "redirect:/";
        } catch (RuntimeException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "error";
        }
    }
}
