package com.spring.statistic.mappers;


import com.spring.statistic.dto.TodolistDto;
import com.spring.statistic.models.Todolist;

public interface TodoListMapper {
    Todolist convertTodoListDtoToTodoList(TodolistDto todoListDto);
    TodolistDto convertTodolistToTodolistDto (Todolist todoList);
}
