package com.spring.statistic.services.scheduler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.spring.statistic.dto.ResumeDayDto;
import com.spring.statistic.rabbitmq.RabbitMessageSender;
import com.spring.statistic.services.StatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SchedulerService {
    private final StatisticsService statisticsService;
    private final RabbitMessageSender rabbitMessageSender;

    @Scheduled(cron = "${cron.expression}")
    public void sendResumeDayToUsers() throws JsonProcessingException {
        List<ResumeDayDto> summaryLists = statisticsService.getSummaryAllUsers();
        for (ResumeDayDto summary : summaryLists) {
            rabbitMessageSender.sendSummaryEmail(summary);
        }
    }
}
