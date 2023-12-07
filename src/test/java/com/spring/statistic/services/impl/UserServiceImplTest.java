package com.spring.statistic.services.impl;

import com.spring.statistic.dto.AppUserDto;
import com.spring.statistic.dto.TaskDto;
import com.spring.statistic.dto.TodolistDto;
import com.spring.statistic.exceptions.NotFoundException;
import com.spring.statistic.mappers.TaskMapper;
import com.spring.statistic.mappers.TodoListMapper;
import com.spring.statistic.mappers.UserMapper;
import com.spring.statistic.models.AppUser;
import com.spring.statistic.models.Task;
import com.spring.statistic.models.Todolist;
import com.spring.statistic.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private TaskMapper taskMapper;
    @Mock
    private TodoListMapper todoListMapper;
    @Mock
    private UserMapper userMapper;
    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void getMadeTaskTest() {
        UUID userId = UUID.randomUUID();
        int countDays = 7;

        Task task1 = new Task();
        task1.setDateTimeCreated(LocalDateTime.now().minusDays(1));
        Task task2 = new Task();
        task2.setDateTimeCreated(LocalDateTime.now().minusDays(2));

        Set<Task> userTasks = Set.of(task1, task2);
        AppUser user = new AppUser();
        user.setTasks(userTasks);

        when(userRepository.findUserById(userId)).thenReturn(Optional.of(user));
        when(taskMapper.convertTaskToTaskDto(task1)).thenReturn(new TaskDto());
        when(taskMapper.convertTaskToTaskDto(task2)).thenReturn(new TaskDto());

        List<TaskDto> result = userService.getMadeTask(userId, countDays);

        assertEquals(userTasks.size(), result.size());
    }

    @Test
    void handleException_testGetMadeTask() {
        UUID userId = UUID.randomUUID();
        int countDays = 7;

        when(userRepository.findUserById(userId)).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class, () -> userService.getMadeTask(userId, countDays));

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
        assertEquals("User not found", exception.getErrorMessage());
        assertNotNull(exception.getTimestamp());

        verify(userRepository, times(1)).findUserById(userId);
        verifyNoInteractions(taskMapper);
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    void getAllUsersTest() {
        List<AppUser> mockUsers = List.of(
                new AppUser(),
                new AppUser()
        );

        when(userRepository.findAll()).thenReturn(mockUsers);
        when(userMapper.convertToUserDto(mockUsers.get(0))).thenReturn(new AppUserDto());
        when(userMapper.convertToUserDto(mockUsers.get(1))).thenReturn(new AppUserDto());

        List<AppUserDto> result = userService.getAllUsers();

        assertEquals(mockUsers.size(), result.size());
    }


    @Test
    void getCompletedTasksTest() {
        UUID userId = UUID.randomUUID();
        int countDays = 7;
        int expectedCount = 2;

        Task completedTask1 = new Task();
        completedTask1.setCompleted(true);
        completedTask1.setDateTimeLastChange(LocalDateTime.now().minusDays(1));

        Task completedTask2 = new Task();
        completedTask2.setCompleted(true);
        completedTask2.setDateTimeLastChange(LocalDateTime.now().minusDays(2));

        Task incompleteTask = new Task();
        incompleteTask.setDateTimeLastChange(LocalDateTime.now().minusDays(3));

        Set<Task> userTasks = Set.of(completedTask1, completedTask2, incompleteTask);
        AppUser user = new AppUser();
        user.setTasks(userTasks);

        when(userRepository.findUserById(userId)).thenReturn(Optional.of(user));
        when(taskMapper.convertTaskToTaskDto(completedTask1)).thenReturn(new TaskDto(completedTask1.getDateTimeLastChange()));
        when(taskMapper.convertTaskToTaskDto(completedTask2)).thenReturn(new TaskDto(completedTask2.getDateTimeLastChange()));

        List<TaskDto> result = userService.getCompletedTasks(userId, countDays);

        assertEquals(expectedCount, result.size());
    }

    @Test
    void handleException_testGetCompletedTasks() {
        UUID userId = UUID.randomUUID();
        int countDays = 5;

        when(userRepository.findUserById(userId)).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class, () -> userService.getCompletedTasks(userId, countDays));

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
        assertEquals("User not found", exception.getErrorMessage());
        assertNotNull(exception.getTimestamp());

        verify(userRepository, times(1)).findUserById(userId);
        verifyNoInteractions(taskMapper);
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    void getAllTasksByUserTest() {
        UUID userId = UUID.randomUUID();

        Task testTask1 = new Task();
        Task testTask2 = new Task();
        Task testTask3 = new Task();

        Set<Task> userTasks = Set.of(testTask1, testTask2, testTask3);
        AppUser user = new AppUser();
        user.setTasks(userTasks);

        when(userRepository.findUserById(userId)).thenReturn(Optional.of(user));
        when(taskMapper.convertTaskToTaskDto(testTask1)).thenReturn(new TaskDto());
        when(taskMapper.convertTaskToTaskDto(testTask2)).thenReturn(new TaskDto());
        when(taskMapper.convertTaskToTaskDto(testTask3)).thenReturn(new TaskDto());

        List<TaskDto> result = userService.getAllTasksByUserId(userId);

        assertEquals(userTasks.size(), result.size());
    }

    @Test
    void handleException_testGetAllTasksByUserId() {
        UUID userId = UUID.randomUUID();

        when(userRepository.findUserById(userId)).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class, () -> userService.getAllTasksByUserId(userId));

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
        assertEquals("User not found", exception.getErrorMessage());
        assertNotNull(exception.getTimestamp());

        verify(userRepository, times(1)).findUserById(userId);
        verifyNoInteractions(taskMapper);
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    void getCompletedTodolistTest() {
        UUID userId = UUID.randomUUID();
        int countDays = 7;
        int expectedCount = 1;

        Task completedTask1 = new Task();
        completedTask1.setCompleted(true);
        completedTask1.setDateTimeLastChange(LocalDateTime.now().minusDays(1));

        Task completedTask2 = new Task();
        completedTask2.setCompleted(true);
        completedTask2.setDateTimeLastChange(LocalDateTime.now().minusDays(2));

        Task completedTask3 = new Task();
        completedTask3.setCompleted(true);
        completedTask3.setDateTimeLastChange(LocalDateTime.now().minusDays(4));

        Task completedTask4 = new Task();
        completedTask4.setCompleted(true);
        completedTask4.setDateTimeLastChange(LocalDateTime.now().minusDays(5));

        Task incompleteTask = new Task();
        incompleteTask.setDateTimeLastChange(LocalDateTime.now().minusDays(3));

        Todolist completedTodolist1 = new Todolist();
        completedTodolist1.setTasks(Set.of(completedTask3, completedTask4));

        Todolist notCompletedTodolist2 = new Todolist();
        notCompletedTodolist2.setTasks(Set.of(completedTask1, completedTask2, incompleteTask));

        Todolist notCompletedTodolist3 = new Todolist();
        notCompletedTodolist3.setTasks(Set.of(incompleteTask));

        Set<Todolist> userTodolists = Set.of(completedTodolist1, notCompletedTodolist2, notCompletedTodolist3);
        AppUser user = new AppUser();
        user.setTodolists(userTodolists);

        when(userRepository.findUserById(userId)).thenReturn(Optional.of(user));
        when(todoListMapper.convertTodolistToTodolistDto(completedTodolist1)).thenReturn(new TodolistDto());

        List<TodolistDto> result = userService.getCompletedTodolist(userId, countDays);

        assertEquals(expectedCount, result.size());
    }

    @Test
    void handleException_testGetCompletedTodolist() {
        UUID userId = UUID.randomUUID();
        int countDays = 7;

        when(userRepository.findUserById(userId)).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class, () -> userService.getCompletedTodolist(userId, countDays));

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
        assertEquals("User not found", exception.getErrorMessage());
        assertNotNull(exception.getTimestamp());

        verify(userRepository, times(1)).findUserById(userId);
        verifyNoInteractions(todoListMapper);
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    void getPastDeadlineTasks() {
        UUID userId = UUID.randomUUID();
        int countDays = 7;
        int expectedCount = 2;

        Task pastDeadlineTask1 = new Task();
        pastDeadlineTask1.setDeadline(LocalDateTime.now().minusDays(1));

        Task pastDeadlineTask2 = new Task();
        pastDeadlineTask2.setDeadline(LocalDateTime.now().minusDays(3));

        Task notPastDeadlineTask = new Task();
        notPastDeadlineTask.setDeadline(LocalDateTime.now().plusDays(1));

        Set<Task> userTasks = Set.of(pastDeadlineTask1, pastDeadlineTask2, notPastDeadlineTask);
        AppUser user = new AppUser();
        user.setTasks(userTasks);

        when(userRepository.findUserById(userId)).thenReturn(Optional.of(user));
        when(taskMapper.convertTaskToTaskDto(pastDeadlineTask1)).thenReturn(new TaskDto());
        when(taskMapper.convertTaskToTaskDto(pastDeadlineTask2)).thenReturn(new TaskDto());

        List<TaskDto> result = userService.getPastDeadlineTasks(userId, countDays);

        assertEquals(expectedCount, result.size());
    }

    @Test
    void handleException_testGetPastDeadlineTasks() {
        UUID userId = UUID.randomUUID();
        int countDays = 7;

        when(userRepository.findUserById(userId)).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class, () -> userService.getPastDeadlineTasks(userId, countDays));

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
        assertEquals("User not found", exception.getErrorMessage());
        assertNotNull(exception.getTimestamp());

        verify(userRepository, times(1)).findUserById(userId);
        verifyNoInteractions(taskMapper);
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    void loadUserByUsernameTest() {
        String login = "user";
        AppUser user = new AppUser();
        user.setUsername(login);

        when(userRepository.findByUsername(login)).thenReturn(Optional.of(user));

        UserDetails result = userService.loadUserByUsername(login);

        verify(userRepository, times(1)).findByUsername(login);
        assertEquals(result.getUsername(), login);
    }

    @Test
    void handleException_loadUserByUsername() {
        String login = "user";
        AppUser user = new AppUser();
        user.setUsername(login);

        when(userRepository.findByUsername(login)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> userService.loadUserByUsername(login));

        verify(userRepository, times(1)).findByUsername(login);
    }
}