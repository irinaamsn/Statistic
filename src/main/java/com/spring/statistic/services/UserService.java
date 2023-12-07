package com.spring.statistic.services;


import com.spring.statistic.dto.AppUserDto;
import com.spring.statistic.dto.TaskDto;
import com.spring.statistic.dto.TodolistDto;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.UUID;

public interface UserService extends UserDetailsService {
    List<TaskDto> getMadeTask(UUID id, int countDays);
    List<AppUserDto> getAllUsers();
    List<TaskDto> getCompletedTasks(UUID id, int countDays);
    List<TaskDto> getAllTasksByUserId(UUID userId);
    List<TodolistDto> getCompletedTodolist(UUID id, int countDays);
    List<TaskDto> getPastDeadlineTasks(UUID id, int countDays);
}
