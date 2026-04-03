package com.prashant.incident_consumer.consumer;

import com.prashant.incident_consumer.model.LogEvent;
import com.prashant.incident_consumer.service.IncidentClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class KafkaLogConsumer {
    private final IncidentClient incidentClient;

    @KafkaListener(topics = "logs-topic", groupId = "incident-group")
    public void consume(@Payload LogEvent logEvent){
        try {
            log.info("Received Logs: {}", logEvent);
            incidentClient.sendToIncidentService(logEvent);
        } catch (Exception e) {
            log.error("Error processing log event: {}", logEvent, e);
        }
    }
}
