package com.spring.statistic.rabbitmq;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.statistic.dto.EmailDto;
import com.spring.statistic.dto.ResumeDayDto;
import com.spring.statistic.dto.enums.QueueNames;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class RabbitMessageSenderIT {
    @Mock
    private RabbitTemplate rabbitTemplate;

    @Mock
    private ObjectMapper objectMapper;

    @Mock
    private RabbitMessageCreator rabbitMessageCreator;

    @InjectMocks
    private RabbitMessageSender rabbitMessageSender;

    @Test
    void sendSummaryEmailTest() throws JsonProcessingException {
        ResumeDayDto summary = new ResumeDayDto("example@mail.com", 2, 3);
        EmailDto emailDto = new EmailDto("example@mail.com", "Обзор дня",
                "Завершено задач за сегодня: " + summary.getCountCompleteTasksOfTheDay() + " из " + summary.getCountAllTasks());

        String expectedJson = "{"
                + "\"receiverEmail\":\"example@mail.com\","
                + "\"header\":\"Обзор дня\","
                + "\"body\":\"Завершено задач за сегодня: 2 из 3\""
                + "}";

        when(rabbitMessageCreator.createSummaryMessage(summary)).thenReturn(emailDto);
        when(objectMapper.writeValueAsString(emailDto)).thenReturn(expectedJson);

        rabbitMessageSender.sendSummaryEmail(summary);

        verify(rabbitTemplate).convertAndSend(eq(QueueNames.SEND_SUMMARY_EMAIL.name()), eq(expectedJson));
    }
}