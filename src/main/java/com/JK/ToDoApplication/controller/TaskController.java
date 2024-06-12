package com.JK.ToDoApplication.controller;

import com.JK.ToDoApplication.models.ToDoTask;
import com.JK.ToDoApplication.service.impl.ToDoServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("api/v1/hometask")
public class TaskController {

    private final ToDoServiceImpl toDoService;

    @GetMapping("/complete")
    public String getCompleteTasks(Model model) {
        List<ToDoTask> completeTasks = toDoService.getCompleteTasks();
        model.addAttribute("taskLists", completeTasks);
        model.addAttribute("taskType", "Complete");
        model.addAttribute("task", new ToDoTask());
        return "index";
    }

    @GetMapping("/incomplete")
    public String getIncompleteTasks(Model model) {
        List<ToDoTask> incompleteTasks = toDoService.getIncompleteTasks();
        model.addAttribute("taskLists", incompleteTasks);
        model.addAttribute("taskType", "Incomplete");
        model.addAttribute("task", new ToDoTask());
        return "index";
    }

    @PostMapping("/addTask")
    public String createTask(@ModelAttribute("task") ToDoTask task, Model model) {
        if (task.getDescription() == null || task.getDescription().isEmpty()) {
            model.addAttribute("errorMessage", "Description is required");
            model.addAttribute("tasks", new ToDoTask());
            List<ToDoTask> tasks = toDoService.getAllTasks(); // Retrieve tasks to display
            model.addAttribute("index", tasks);
            return "redirect:/error/descriptionRequired"; // Return to the form if the description is missing
        }
        toDoService.createTask(task);
        return "redirect:/api/v1/hometask/";
    }

    @RequestMapping(path = "/del/{taskID}")
    public String deleteTask (@PathVariable("taskID") Long studentID){
        toDoService.deleteTask(studentID);
        return "redirect:/api/v1/hometask/";
    }

    @RequestMapping(path = "/del/all")
    public String deleteAllTasks(){
        toDoService.deleteAllTask();
        return "redirect:/api/v1/hometask/";
    }

    @RequestMapping("/tasks/{id}")
    public String updateTask(@PathVariable Long id,
                             @ModelAttribute("task") ToDoTask task,
                             RedirectAttributes redirectAttributes) {
        try {
            ToDoTask existingTask = toDoService.getTaskById(id);
            if (existingTask != null) {
                existingTask.setIsComplete(!existingTask.getIsComplete()); // Toggle completion status
                toDoService.updateTask(existingTask, id);
            }
            return "redirect:/api/v1/hometask/";
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/error";
        }
    }

}
