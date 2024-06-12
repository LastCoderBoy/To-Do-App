package com.JK.ToDoApplication.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/error")
public class ErrorController {

    @GetMapping
    public String handleError(Model model) {
        model.addAttribute("errorMessage", "An unexpected error occurred.");
        return "error";
    }

    @GetMapping("/descriptionRequired")
    public String handleDescriptionRequiredError(Model model) {
        model.addAttribute("errorMessage", "Description is required.");
        return "error";
    }
}

