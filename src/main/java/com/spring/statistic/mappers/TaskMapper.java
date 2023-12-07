package com.spring.statistic.mappers;


import com.spring.statistic.dto.TaskDto;
import com.spring.statistic.models.Task;


public interface TaskMapper {

    Task convertTaskDtoToTask(TaskDto taskDto);
    TaskDto convertTaskToTaskDto(Task taskDto);
}
