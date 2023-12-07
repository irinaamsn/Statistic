package com.spring.statistic.services;

import com.spring.statistic.dto.StatisticDto;
import com.spring.statistic.dto.ResumeDayDto;

import java.util.List;
import java.util.UUID;

public interface StatisticsService {
    StatisticDto getStatistics(UUID userId, int countDays);
    List<ResumeDayDto> getSummaryAllUsers();
}
