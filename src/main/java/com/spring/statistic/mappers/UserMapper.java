package com.spring.statistic.mappers;


import com.spring.statistic.dto.AppUserDto;
import com.spring.statistic.models.AppUser;

public interface UserMapper {
    AppUser convertToUser(AppUserDto userDto);
    AppUserDto convertToUserDto(AppUser user);
}
