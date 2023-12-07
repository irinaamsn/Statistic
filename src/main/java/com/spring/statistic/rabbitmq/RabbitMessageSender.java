package com.spring.statistic.rabbitmq;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.statistic.dto.EmailDto;
import com.spring.statistic.dto.ResumeDayDto;
import com.spring.statistic.dto.enums.QueueNames;
import com.spring.statistic.exceptions.RabbitSendException;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RabbitMessageSender {
    private final RabbitMessageCreator rabbitMessageCreator;
    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;

    public void sendSummaryEmail(ResumeDayDto summary){
        String queueName = QueueNames.SEND_SUMMARY_EMAIL.name();
        EmailDto emailDto = rabbitMessageCreator.createSummaryMessage(summary);
        try {
            rabbitTemplate.convertAndSend(queueName, objectMapper.writeValueAsString(emailDto));
        } catch (JsonProcessingException e) {
            throw new RabbitSendException(HttpStatus.INTERNAL_SERVER_ERROR, "Error send message", System.currentTimeMillis());
        }
    }
}
