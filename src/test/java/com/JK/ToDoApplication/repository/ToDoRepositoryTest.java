package com.JK.ToDoApplication.repository;

import com.JK.ToDoApplication.TestDataUtil;
import com.JK.ToDoApplication.models.ToDoTask;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.Instant;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class ToDoRepositoryTest {
    @Autowired
    private ToDoRepository underTest;

    @AfterEach
    void tearDown() {
        underTest.deleteAll();
    }

    @Test
    void shouldGetIncompleteTasks() {

        //given
        ToDoTask task1 = TestDataUtil.Task1();
        ToDoTask task2 = TestDataUtil.Task2();
        ToDoTask task3 = TestDataUtil.Task3();

        underTest.save(task1); //false task
        underTest.save(task2); //false task
        underTest.save(task3); //true task

        //when
        List<ToDoTask> incompleteTasks = underTest.findToDoTaskByIsComplete(false);

        //then
        assertThat(incompleteTasks).hasSize(2)
                .extracting("description").contains("Send email");

    }

    @Test
    void shouldGetCompleteTasks(){
        //given
        ToDoTask task1 = TestDataUtil.Task1();
        ToDoTask task2 = TestDataUtil.Task2();
        ToDoTask task3 = TestDataUtil.Task3();

        underTest.save(task1); //false task
        underTest.save(task2); //false task
        underTest.save(task3); //true task

        //when
        List<ToDoTask> completeTasks = underTest.findToDoTaskByIsComplete(true);

        //then
        assertThat(completeTasks).hasSize(1)
                .extracting("description").contains("Pray Namaz");
    }
}