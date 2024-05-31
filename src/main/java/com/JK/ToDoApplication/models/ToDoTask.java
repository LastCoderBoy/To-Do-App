package com.JK.ToDoApplication.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;
import java.util.Objects;

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

    public ToDoTask(Long id, String description, Boolean isComplete, Instant createdAt) {
        this.id = id;
        this.description = description;
        this.isComplete = isComplete;
        this.createdAt = createdAt;
    }

    public ToDoTask(Long id, String description) {
        this.id = id;
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ToDoTask toDoTask = (ToDoTask) o;
        return isComplete == toDoTask.isComplete &&
                Objects.equals(id, toDoTask.id) &&
                Objects.equals(description, toDoTask.description);
        // Exclude updatedAt from equality check if necessary
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, description, isComplete);
        // Exclude updatedAt from hash code calculation if necessary
    }

}
