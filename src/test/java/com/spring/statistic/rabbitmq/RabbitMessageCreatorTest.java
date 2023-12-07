package com.spring.statistic.rabbitmq;

import com.spring.statistic.dto.EmailDto;
import com.spring.statistic.dto.ResumeDayDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
class RabbitMessageCreatorTest {
    @InjectMocks
    private RabbitMessageCreator rabbitMessageCreator;

    @Test
    void createSummaryMessageTest() {
        ResumeDayDto summary = new ResumeDayDto("example@mail.com", 2, 3);

        EmailDto result = rabbitMessageCreator.createSummaryMessage(summary);

        assertEquals("example@mail.com", result.getReceiverEmail());
        assertEquals("Обзор дня", result.getHeader());
        assertEquals("Завершено задач за сегодня: 2 из 3", result.getBody());
    }
}