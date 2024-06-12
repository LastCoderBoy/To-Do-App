package com.JK.ToDoApplication.controller;

import com.JK.ToDoApplication.models.ToDoTask;
import com.JK.ToDoApplication.service.impl.ToDoServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/api/v1/hometask")
public class HomeController {

    private final ToDoServiceImpl toDoService;

    @GetMapping("/")
    public String index(Model model){
        try {
            List<ToDoTask> toDoTaskList = toDoService.getAllTasks();
            model.addAttribute("taskLists", toDoTaskList);
            model.addAttribute("task", new ToDoTask());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "error";
        }
        return "index";
    }
}
