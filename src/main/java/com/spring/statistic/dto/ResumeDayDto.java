package com.spring.statistic.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResumeDayDto {
    private String receiverEmail;
    private int countCompleteTasksOfTheDay;
    private int countAllTasks;
}
