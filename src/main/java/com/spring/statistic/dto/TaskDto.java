package com.spring.statistic.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TaskDto {
    private UUID id;
    private String title;
    private String details;
    private LocalDateTime dateTimeLastChange;
    private LocalDateTime dateTimeCreated;
    private LocalDateTime deadline;
    private boolean isCompleted;

    public long getExecutionTime() {
        return Duration.between(dateTimeCreated, dateTimeLastChange).getSeconds();
    }

    public TaskDto(LocalDateTime dateTimeLastChange, LocalDateTime dateTimeCreated, LocalDateTime deadline) {
        this.dateTimeLastChange = dateTimeLastChange;
        this.dateTimeCreated = dateTimeCreated;
        this.deadline = deadline;
    }

    public TaskDto(LocalDateTime dateTimeLastChange) {
        this.dateTimeLastChange = dateTimeLastChange;
    }

    public TaskDto(LocalDateTime dateTimeLastChange, boolean isCompleted) {
        this.dateTimeLastChange = dateTimeLastChange;
        this.isCompleted = isCompleted;
    }
}
