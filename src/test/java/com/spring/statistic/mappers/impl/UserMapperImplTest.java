package com.spring.statistic.mappers.impl;

import com.spring.statistic.dto.AppUserDto;
import com.spring.statistic.exceptions.MapperCovertException;
import com.spring.statistic.models.AppUser;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
class UserMapperImplTest {
    @InjectMocks
    private UserMapperImpl userMapper;

    @Test
    void convertToUser() {
        AppUserDto userDto = new AppUserDto(UUID.randomUUID(), "test@example.com", "username", "password");

        AppUser result = userMapper.convertToUser(userDto);

        assertNotNull(result);
        assertEquals(userDto.getEmail(), result.getEmail());
        assertEquals(userDto.getUsername(), result.getUsername());
    }

    @Test
    void handleException_NullUserDto_convertToUser() {
        AppUserDto userDto = null;
        assertThrows(MapperCovertException.class, () -> {userMapper.convertToUser(userDto);});
    }

    @Test
    void convertToUserDto() {
        AppUser user = new AppUser(UUID.randomUUID(), "test@example.com", "username", "password");

        AppUserDto result = userMapper.convertToUserDto(user);

        assertNotNull(result);
        assertEquals(user.getEmail(), result.getEmail());
        assertEquals(user.getUsername(), result.getUsername());
    }

    @Test
    void handleException_NullUser_convertToUserDto() {
        AppUser user = null;
        assertThrows(MapperCovertException.class, () -> {userMapper.convertToUserDto(user);});
    }
}