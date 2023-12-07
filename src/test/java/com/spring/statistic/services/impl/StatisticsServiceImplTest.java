package com.spring.statistic.services.impl;

import com.spring.statistic.dto.*;
import com.spring.statistic.services.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StatisticsServiceImplTest {
    @Mock
    private UserService userService;

    @InjectMocks
    private StatisticsServiceImpl statisticsService;

    @Test
    void getStatisticsWeekTest() {
        UUID userId = UUID.randomUUID();
        int countDays = 7;

        List<TaskDto> mockCompletedTasks = List.of(
                new TaskDto(LocalDateTime.now(), LocalDateTime.now().minusDays(2), LocalDateTime.now().plusDays(1)),
                new TaskDto(LocalDateTime.now(), LocalDateTime.now().minusDays(3), LocalDateTime.now().minusDays(1))
        );
        List<TodolistDto> mockCompletedTodolist = List.of(new TodolistDto(), new TodolistDto());
        List<TaskDto> mockPastDeadlinesTasks = List.of(new TaskDto());
        List<TaskDto> mockMadeTask = List.of(new TaskDto());
        List<TaskDto> mockCompletedTaskBeforeDeadline = List.of(mockCompletedTasks.get(0));

        when(userService.getCompletedTasks(userId, countDays)).thenReturn(mockCompletedTasks);
        when(userService.getCompletedTodolist(userId, countDays)).thenReturn(mockCompletedTodolist);
        when(userService.getPastDeadlineTasks(userId, countDays)).thenReturn(mockPastDeadlinesTasks);
        when(userService.getMadeTask(userId, countDays)).thenReturn(mockMadeTask);
        //when(userService.getCompletedTasksBeforeDeadline(userId, countDays)).thenReturn(mockCompletedTaskBeforeDeadline);

        StatisticDto result = statisticsService.getStatistics(userId, countDays);

        assertEquals(mockCompletedTaskBeforeDeadline.size(), result.getCountCompletedTasksBeforeDeadline());
        assertEquals(mockCompletedTasks.size(), result.getCountCompletedTask());
        assertEquals(mockCompletedTasks.size() - mockCompletedTaskBeforeDeadline.size(), result.getCountCompletedTasksAfterDeadline());
        assertEquals(mockPastDeadlinesTasks.size(), result.getCountPastTask());
        assertEquals(mockCompletedTodolist, result.getTodolistDtoLists());
        assertEquals(mockCompletedTasks.size() - mockPastDeadlinesTasks.size(), result.getCountCompletedTasksBeforeDeadline());
        assertEquals(mockMadeTask.size(), result.getCountMadeTask());
    }

    @Test
    void getSummaryAllUsersTest() {
        List<ResumeDayDto> expectedSummaryList = List.of(
                new ResumeDayDto("user1@example.com", 2, 2),
                new ResumeDayDto("user2@example.com", 3, 3)
        );

        List<AppUserDto> mockUsers = List.of(
                new AppUserDto(UUID.randomUUID(), "user1@example.com", "user1", "password1"),
                new AppUserDto(UUID.randomUUID(), "user2@example.com", "user2", "password2")
        );

        when(userService.getAllUsers()).thenReturn(mockUsers);

        when(userService.getAllTasksByUserId(mockUsers.get(0).getId())).thenReturn(List.of(new TaskDto(LocalDateTime.now(),true), new TaskDto(LocalDateTime.now(),true)));
        when(userService.getAllTasksByUserId(mockUsers.get(1).getId())).thenReturn(List.of(new TaskDto(LocalDateTime.now(),true), new TaskDto(LocalDateTime.now(),true), new TaskDto(LocalDateTime.now(),true)));

        List<ResumeDayDto> result = statisticsService.getSummaryAllUsers();

        assertEquals(expectedSummaryList.size(), result.size());

        for (int i = 0; i < expectedSummaryList.size(); i++) {
            assertEquals(expectedSummaryList.get(i).getCountCompleteTasksOfTheDay(), result.get(i).getCountCompleteTasksOfTheDay());
            assertEquals(expectedSummaryList.get(i).getCountAllTasks(), result.get(i).getCountAllTasks());
            assertEquals(expectedSummaryList.get(i).getReceiverEmail(), result.get(i).getReceiverEmail());
        }
    }
}