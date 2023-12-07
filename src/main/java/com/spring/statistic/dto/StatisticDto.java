package com.spring.statistic.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StatisticDto {
    private String avgTimeCompleteTask;
    private int countMadeTask;
    private int countCompletedTasksBeforeDeadline;
    private int countCompletedTasksAfterDeadline;
    private List<TodolistDto> todolistDtoLists;
    private int countCompletedTask;
    private int countPastTask;
}
