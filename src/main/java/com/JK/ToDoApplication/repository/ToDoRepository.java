package com.JK.ToDoApplication.repository;


import com.JK.ToDoApplication.models.ToDoTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ToDoRepository extends JpaRepository<ToDoTask, Long> {

    @Query("SELECT t FROM ToDoTask t WHERE t.isComplete = :condition")
    List<ToDoTask> findToDoTaskByIsComplete(boolean condition);
}
