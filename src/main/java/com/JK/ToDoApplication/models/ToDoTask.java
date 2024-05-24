package com.JK.ToDoApplication.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;

@Entity
@AllArgsConstructor
@Data
@Table(name = "To_Do_Tasks")
public class ToDoTask {

    @GeneratedValue
    @Id
    private Long id;
    private String description;
    private Boolean isComplete;
    private Instant createdAt;
    private Instant updatedAt;

    public ToDoTask() {}
}
