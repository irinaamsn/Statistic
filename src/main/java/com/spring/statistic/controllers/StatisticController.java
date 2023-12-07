package com.spring.statistic.controllers;

import com.spring.statistic.dto.StatisticDto;
import com.spring.statistic.services.StatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.UUID;
@Controller
@RequestMapping("/remote/api/statistic/{id}")
@RequiredArgsConstructor
public class StatisticController {
    private final StatisticsService statisticsService;

    @GetMapping("/month")
    public ResponseEntity<?> getMonthlyStatistics(@PathVariable(name = "id") UUID userId) {
        StatisticDto monthlyStatistics = statisticsService.getStatistics(userId, 30);
        return ResponseEntity.ok(monthlyStatistics);
    }


    @GetMapping("/week")
    public ResponseEntity<?> getWeeklyStatistics(@PathVariable(name = "id") UUID userId) {
        StatisticDto weeklyStatistics = statisticsService.getStatistics(userId, 7);
        return ResponseEntity.ok(weeklyStatistics);
    }
}
