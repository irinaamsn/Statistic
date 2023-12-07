package com.spring.statistic.mappers.impl;

import com.spring.statistic.dto.AppUserDto;
import com.spring.statistic.exceptions.MapperCovertException;
import com.spring.statistic.mappers.UserMapper;
import com.spring.statistic.models.AppUser;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class UserMapperImpl implements UserMapper {
    @Override
    public AppUser convertToUser(AppUserDto userDto) {
        if (userDto == null)
            throw new MapperCovertException(HttpStatus.BAD_REQUEST, "Invalid data userDto", System.currentTimeMillis());
        AppUser user = new AppUser();
        user.setId(userDto.getId());
        user.setEmail(userDto.getEmail());
        user.setUsername(userDto.getUsername());
        user.setPassword(userDto.getPassword());
        return user;
    }

    @Override
    public AppUserDto convertToUserDto(AppUser user) {
        if (user == null)
            throw new MapperCovertException(HttpStatus.BAD_REQUEST, "Invalid data user", System.currentTimeMillis());
        AppUserDto userDto = new AppUserDto();
        userDto.setId(user.getId());
        userDto.setEmail(user.getEmail());
        userDto.setUsername(user.getUsername());
        userDto.setPassword(user.getPassword());
        return userDto;
    }
}
