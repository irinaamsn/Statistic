package com.spring.statistic.mappers.impl;

import com.spring.statistic.dto.TaskDto;
import com.spring.statistic.exceptions.MapperCovertException;
import com.spring.statistic.mappers.TaskMapper;
import com.spring.statistic.models.Task;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class TaskMapperImpl implements TaskMapper {
    @Override
    public Task convertTaskDtoToTask(TaskDto taskDto) {
        if ( taskDto == null ) {
            throw new MapperCovertException(HttpStatus.BAD_REQUEST, "Invalid data task", System.currentTimeMillis());
        }
        Task task = new Task();
        task.setDateTimeCreated(taskDto.getDateTimeCreated());
        task.setTitle( taskDto.getTitle() );
        task.setDetails( taskDto.getDetails() );
        task.setDateTimeLastChange( taskDto.getDateTimeLastChange() );
        task.setDeadline(taskDto.getDeadline());
        task.setCompleted( taskDto.isCompleted() );
        return task;
    }

    @Override
    public TaskDto convertTaskToTaskDto(Task task) {
        if ( task == null ) {
            throw new MapperCovertException(HttpStatus.BAD_REQUEST, "Invalid data task", System.currentTimeMillis());
        }
        TaskDto taskDto = new TaskDto();
        taskDto.setDateTimeCreated(task.getDateTimeCreated());
        taskDto.setId(task.getId());
        taskDto.setTitle( task.getTitle() );
        taskDto.setDetails( task.getDetails() );
        taskDto.setDeadline(task.getDeadline());
        taskDto.setDateTimeLastChange( task.getDateTimeLastChange() );
        taskDto.setCompleted( task.isCompleted() );

        return taskDto;
    }
}
