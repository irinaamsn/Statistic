package com.spring.statistic.services.impl;

import com.spring.statistic.services.StatisticsService;
import com.spring.statistic.dto.*;
import com.spring.statistic.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StatisticsServiceImpl implements StatisticsService {
    private final UserService userService;

    @Override
    public StatisticDto getStatistics(UUID userId, int countDays) {
        List<TaskDto> completedTasks = userService.getCompletedTasks(userId, countDays);
        List<TodolistDto> completedTodolist = userService.getCompletedTodolist(userId, countDays);
        List<TaskDto> pastDeadlinesTasks = userService.getPastDeadlineTasks(userId, countDays);
        List<TaskDto> createTasks = userService.getMadeTask(userId, countDays);
        List<TaskDto> completedTasksBeforeDeadline = completedTasks.stream()
                .filter(t -> t.getDeadline().compareTo(t.getDateTimeLastChange()) >= 0)
                .toList();
        long totalExecutionTimeInSeconds = completedTasks.stream()
                .mapToLong(TaskDto::getExecutionTime)
                .sum();
        return new StatisticDto(convertSecondsToHumanReadable(totalExecutionTimeInSeconds / (double) completedTasks.size()),
                createTasks.size(),
                completedTasksBeforeDeadline.size(),
                completedTasks.size() - completedTasksBeforeDeadline.size(),
                completedTodolist,
                completedTasks.size(),
                pastDeadlinesTasks.size()
        );
    }

    @Override
    public List<ResumeDayDto> getSummaryAllUsers() {
        List<ResumeDayDto> summaryList = new LinkedList<>();
        List<AppUserDto> users = userService.getAllUsers();
        for (AppUserDto user : users) {
            List<TaskDto> tasks = userService.getAllTasksByUserId(user.getId());
            List<TaskDto> completedTasksOfTheDay = tasks.stream()
                    .filter(t -> t.isCompleted() && t.getDateTimeLastChange().toLocalDate().compareTo(LocalDate.now()) == 0)
                    .toList();
            ResumeDayDto resumeDayDto = new ResumeDayDto(user.getEmail(), completedTasksOfTheDay.size(), tasks.size());
            summaryList.add(resumeDayDto);
        }
        return summaryList;
    }

    private String convertSecondsToHumanReadable(double seconds) {
        long minutes = (long) seconds / 60;
        long remainingSeconds = (long) seconds % 60;

        long hours = minutes / 60;
        long remainingMinutes = minutes % 60;

        long days = hours / 24;
        long remainingHours = hours % 24;

        StringBuilder result = new StringBuilder();

        if (days > 0) {
            result.append(days).append(" дней ");
        }

        if (remainingHours > 0) {
            result.append(remainingHours).append(" часов ");
        }

        if (remainingMinutes > 0) {
            result.append(remainingMinutes).append(" минут ");
        }

        if (remainingSeconds > 0) {
            result.append(remainingSeconds).append(" секунд ");
        }

        return result.toString().trim();
    }
}
