package com.JK.ToDoApplication;

import com.JK.ToDoApplication.models.ToDoTask;

import java.time.Instant;

public final class TestDataUtil {
    public static ToDoTask Task1() {
        return new ToDoTask(1L, "Send email", false, Instant.now());
    }
    public static ToDoTask Task2(){
        return new ToDoTask(2L, "Check grade", false, Instant.now());
    }
    public static ToDoTask Task3(){
        return new ToDoTask(3L, "Pray Namaz", true, Instant.now(), Instant.now());
    }
    public static ToDoTask Task4(){
        return new ToDoTask(4L, "Make food for supper", true, Instant.now(), Instant.now());
    }
    public static ToDoTask Task5(){
        return new ToDoTask(5L, "Check the Email");
    }
}
