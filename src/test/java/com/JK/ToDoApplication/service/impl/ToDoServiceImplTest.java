package com.JK.ToDoApplication.service.impl;

import com.JK.ToDoApplication.TestDataUtil;
import com.JK.ToDoApplication.models.ToDoTask;
import com.JK.ToDoApplication.repository.ToDoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;

import java.time.Instant;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ToDoServiceImplTest {
    @Mock
    private ToDoRepository toDoRepository;
    @InjectMocks
    private ToDoServiceImpl underTest;

    @BeforeEach
    void setUp() {
        underTest = new ToDoServiceImpl(toDoRepository);
    }

    @Test
    void shouldGetAllTasks() {
        //when
        underTest.getAllTasks();
        //then
        verify(toDoRepository).findAll();
    }

    @Test
    void shouldGetCompleteTasks() {
        //when
        underTest.getCompleteTasks();
        //then
        verify(toDoRepository).findToDoTaskByIsComplete(true);
    }

    @Test
    void shouldGetIncompleteTasks() {
        //when
        underTest.getIncompleteTasks();
        //then
        verify(toDoRepository).findToDoTaskByIsComplete(false);
    }

    @Test
    void shouldCreateTask() {
        //given
        ToDoTask task = TestDataUtil.Task5();

        //when
        underTest.createTask(task);
        ArgumentCaptor<ToDoTask> toDoTaskArgumentCaptor =
                ArgumentCaptor.forClass(ToDoTask.class);
        //then
        verify(toDoRepository).save(toDoTaskArgumentCaptor.capture());

        ToDoTask capturedTask = toDoTaskArgumentCaptor.getValue();
        assertThat(capturedTask).isEqualTo(task);
    }

    @Test
    void deleteTask() {
        //make sure the task is created successfully
        ToDoTask task = TestDataUtil.Task1();
        underTest.createTask(task);
        ArgumentCaptor<ToDoTask> toDoTaskArgumentCaptor =
                ArgumentCaptor.forClass(ToDoTask.class);
        verify(toDoRepository).save(toDoTaskArgumentCaptor.capture());

        ToDoTask capturedTask = toDoTaskArgumentCaptor.getValue();
        assertThat(capturedTask).isEqualTo(task);

        // Let's check the deleting functionality
        underTest.deleteTask(task.getId());
        Optional<ToDoTask> result = toDoRepository.findById(task.getId());
        assertThat(result).isEmpty();
    }

    @Test
    void deleteTaskWithInvalidId() {
        // Verify exception is thrown for invalid task ID
        Long invalidId = 999L;
        doThrow(new EmptyResultDataAccessException(1)).when(toDoRepository).deleteById(invalidId);

        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            underTest.deleteTask(invalidId);
        });

        assertThat(exception.getMessage()).isEqualTo("Task not found with ID: " + invalidId);
    }
    @Test
    void deleteTaskWithNullId() {
        // Verify exception is thrown for null task ID
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            underTest.deleteTask(null);
        });

        assertThat(exception.getMessage()).isEqualTo("Task ID cannot be null");
    }
    @Test
    void deleteAllTask() {
        ToDoTask task1 = TestDataUtil.Task1();
        ToDoTask task2 = TestDataUtil.Task2();
        ToDoTask task3 = TestDataUtil.Task3();
        underTest.createTask(task1);
        verify(toDoRepository).save(task1);

        underTest.createTask(task2);
        verify(toDoRepository).save(task2);

        underTest.createTask(task3);
        verify(toDoRepository).save(task3);

        underTest.deleteAllTask();
        verify(toDoRepository).deleteAll();
    }
    @Test
    void deleteAllTaskHandlesException() {
        // Arrange
        doThrow(new DataAccessException("...") {}).when(toDoRepository).deleteAll();

        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            underTest.deleteAllTask();
        });

        assertThat(exception.getMessage()).isEqualTo("Failed to delete all tasks");
    }

    @Test
    void shouldUpdateTask() {
        ToDoTask task = TestDataUtil.Task5();

        given(toDoRepository.save(any(ToDoTask.class))).willAnswer(invocation -> invocation.getArgument(0));
        given(toDoRepository.findById(task.getId())).willReturn(Optional.of(task));
        underTest.createTask(task);

        Instant fixedInstant = Instant.now();
        task.setUpdatedAt(fixedInstant);
        task.setIsComplete(true);
        underTest.updateTask(task, task.getId());

        Optional<ToDoTask> result = toDoRepository.findById(task.getId());

        assertThat(result.get()).isEqualTo(task);
    }

    @Test
    void updateTaskShouldFail(){
        ToDoTask task = TestDataUtil.Task5();

        underTest.createTask(task);

        Instant fixedInstant = Instant.now();
        task.setUpdatedAt(fixedInstant);
        task.setIsComplete(true);

        Optional<ToDoTask> result = toDoRepository.findById(task.getId());

        assertThatThrownBy(() -> underTest.updateTask(task, task.getId()))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Task not found with ID: " + task.getId());
    }

}