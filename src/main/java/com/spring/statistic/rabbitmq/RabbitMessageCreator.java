package com.spring.statistic.rabbitmq;

import com.spring.statistic.dto.EmailDto;
import com.spring.statistic.dto.ResumeDayDto;
import org.springframework.stereotype.Service;

@Service
public class RabbitMessageCreator {
    public EmailDto createSummaryMessage(ResumeDayDto summary) {
        EmailDto emailDto = new EmailDto();
        String headerEmail = "Обзор дня";
        emailDto.setReceiverEmail(summary.getReceiverEmail());
        emailDto.setHeader(headerEmail);
        String bodyEmail = String.format("Завершено задач за сегодня: %d из %d", summary.getCountCompleteTasksOfTheDay(), summary.getCountAllTasks());
        emailDto.setBody(bodyEmail);
        return emailDto;
    }
}
