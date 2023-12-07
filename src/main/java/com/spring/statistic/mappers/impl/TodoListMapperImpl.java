package com.spring.statistic.mappers.impl;


import com.spring.statistic.dto.TodolistDto;
import com.spring.statistic.exceptions.MapperCovertException;
import com.spring.statistic.mappers.TodoListMapper;
import com.spring.statistic.models.Todolist;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class TodoListMapperImpl implements TodoListMapper {
    @Override
    public Todolist convertTodoListDtoToTodoList(TodolistDto todoListDto) {
        if ( todoListDto == null ) {
            throw new MapperCovertException(HttpStatus.BAD_REQUEST, "Invalid data todolist", System.currentTimeMillis());
        }
        Todolist todoList = new Todolist();
        todoList.setTitle( todoListDto.getTitle() );
        return todoList;
    }

    @Override
    public TodolistDto convertTodolistToTodolistDto(Todolist todoList) {
        if ( todoList == null ) {
            throw new MapperCovertException(HttpStatus.BAD_REQUEST, "Invalid data todolist", System.currentTimeMillis());
        }

        TodolistDto todoListDto = new TodolistDto();
        todoListDto.setId(todoList.getId());
        todoListDto.setTitle( todoList.getTitle() );
        return todoListDto;
    }
}
