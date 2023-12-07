package com.spring.statistic.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.statistic.dto.StatisticDto;
import com.spring.statistic.dto.TodolistDto;
import com.spring.statistic.services.StatisticsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class StatisticControllerIT {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private StatisticsService statisticsService;
    @Autowired
    private WebApplicationContext context;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    @WithMockUser(username = "user")
    void getMonthlyStatistics() throws Exception {
        UUID userId = UUID.randomUUID();
        StatisticDto mockMonthlyStatistics = new StatisticDto("1 день", 4, 1, 1, List.of(new TodolistDto()), 2, 0);

        when(statisticsService.getStatistics(userId, 30)).thenReturn(mockMonthlyStatistics);

        mockMvc.perform(MockMvcRequestBuilders.get("/remote/api/statistic/{id}/month",userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "user")
                        .header("X-User-Roles", "ROLE_USER"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(mockMonthlyStatistics)));
        verify(statisticsService, times(1)).getStatistics(userId, 30);
    }

    @Test
    @WithMockUser(username = "user")
    void getWeeklyStatistics() throws Exception {
        UUID userId = UUID.randomUUID();
        StatisticDto mockMonthlyStatistics = new StatisticDto("1 день", 4, 1, 1, List.of(new TodolistDto()), 2, 0);

        when(statisticsService.getStatistics(userId, 7)).thenReturn(mockMonthlyStatistics);

        mockMvc.perform(MockMvcRequestBuilders.get("/remote/api/statistic/{id}/week",userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "user")
                        .header("X-User-Roles", "ROLE_USER"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(mockMonthlyStatistics)));
        verify(statisticsService, times(1)).getStatistics(userId, 7);
    }
}