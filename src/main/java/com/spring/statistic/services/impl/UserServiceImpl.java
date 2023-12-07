package com.spring.statistic.services.impl;

import com.spring.statistic.details.AppUserDetails;
import com.spring.statistic.dto.AppUserDto;
import com.spring.statistic.dto.TaskDto;
import com.spring.statistic.dto.TodolistDto;
import com.spring.statistic.exceptions.NotFoundException;
import com.spring.statistic.mappers.TaskMapper;
import com.spring.statistic.mappers.TodoListMapper;
import com.spring.statistic.mappers.UserMapper;
import com.spring.statistic.models.AppUser;
import com.spring.statistic.repositories.UserRepository;
import com.spring.statistic.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final TaskMapper taskMapper;
    private final TodoListMapper todoListMapper;
    private final UserMapper userMapper;

    @Override
    public List<TaskDto> getMadeTask(UUID id, int countDays) {
        return userRepository.findUserById(id)
                .orElseThrow(() -> new NotFoundException(HttpStatus.NOT_FOUND, "User not found", System.currentTimeMillis()))
                .getTasks().stream()
                .filter(t -> t.getDateTimeCreated().compareTo(LocalDateTime.now().minusDays(countDays)) >= 0)
                .map(taskMapper::convertTaskToTaskDto)
                .toList();
    }

    @Override
    public List<AppUserDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(userMapper::convertToUserDto)
                .toList();
    }

    @Override
    public List<TaskDto> getCompletedTasks(UUID id, int countDays) {
        return userRepository.findUserById(id)
                .orElseThrow(() -> new NotFoundException(HttpStatus.NOT_FOUND, "User not found", System.currentTimeMillis()))
                .getTasks().stream()
                .filter(t -> t.isCompleted() &&
                        t.getDateTimeLastChange().compareTo(LocalDateTime.now().minusDays(countDays)) >= 0)
                .map(taskMapper::convertTaskToTaskDto)
                .toList();
    }

    @Override
    public List<TaskDto> getAllTasksByUserId(UUID userId) {
        return userRepository.findUserById(userId)
                .orElseThrow(() -> new NotFoundException(HttpStatus.NOT_FOUND, "User not found", System.currentTimeMillis()))
                .getTasks().stream()
                .map(taskMapper::convertTaskToTaskDto)
                .toList();
    }

    @Override
    public List<TodolistDto> getCompletedTodolist(UUID id, int countDays) {
        return userRepository.findUserById(id)
                .orElseThrow(() -> new NotFoundException(HttpStatus.NOT_FOUND, "User not found", System.currentTimeMillis()))
                .getTodolists().stream()
                .filter(tl -> !tl.getTasks().isEmpty())
                .filter(tl -> tl.getTasks().stream()
                        .allMatch(t -> t.isCompleted() &&
                                t.getDateTimeLastChange().compareTo(LocalDateTime.now().minusDays(countDays)) >= 0))
                .map(todoListMapper::convertTodolistToTodolistDto)
                .toList();
    }

    @Override
    public List<TaskDto> getPastDeadlineTasks(UUID id, int countDays) {
        return userRepository.findUserById(id)
                .orElseThrow(() -> new NotFoundException(HttpStatus.NOT_FOUND, "User not found", System.currentTimeMillis()))
                .getTasks().stream()
                .filter(t -> t.getDeadline().compareTo(LocalDateTime.now()) < 0 && !t.isCompleted() &&
                        t.getDeadline().compareTo(LocalDateTime.now().minusDays(countDays)) >= 0)
                .map(taskMapper::convertTaskToTaskDto)
                .toList();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser user = userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException(HttpStatus.NOT_FOUND, "User not found", System.currentTimeMillis()));
        return new AppUserDetails(user);
    }
}
