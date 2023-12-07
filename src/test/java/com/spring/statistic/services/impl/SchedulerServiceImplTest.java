package com.spring.statistic.services.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.spring.statistic.dto.ResumeDayDto;
import com.spring.statistic.rabbitmq.RabbitMessageSender;
import com.spring.statistic.services.scheduler.SchedulerService;
import com.spring.statistic.services.StatisticsService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@SpringBootTest
@EnableScheduling
class SchedulerServiceImplTest {
    @Mock
    private StatisticsService statisticsService;

    @Mock
    private RabbitMessageSender rabbitMessageSender;

    @InjectMocks
    private SchedulerService schedulerService;

    @Test
    void sendResumeDayToUsers() throws JsonProcessingException {
        ResumeDayDto resumeDayDto  = new ResumeDayDto("example@mail.com", 2,3);
        List<ResumeDayDto> summaryLists = Collections.singletonList(resumeDayDto);

        when(statisticsService.getSummaryAllUsers()).thenReturn(summaryLists);

        CountDownLatch latch = new CountDownLatch(1);

        ConcurrentTaskScheduler taskScheduler = new ConcurrentTaskScheduler();
        ScheduledFuture<?> future = taskScheduler.schedule(() -> {
            try {
                schedulerService.sendResumeDayToUsers();
                latch.countDown();
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }, new CronTrigger("*/10 * * * * *"));

        try {
            assertTrue(latch.await(10, TimeUnit.SECONDS));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        verify(statisticsService, times(1)).getSummaryAllUsers();

        future.cancel(true);
    }
}