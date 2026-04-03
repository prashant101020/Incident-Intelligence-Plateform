package com.prashant.incident_consumer.consumer;

import com.prashant.incident_consumer.Util.SeverityUtil;
import com.prashant.incident_consumer.model.LogEvent;
import com.prashant.incident_consumer.service.IncidentClient;
import com.prashant.incident_consumer.service.RedisService;
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
    private final RedisService redisService;
    private final SeverityUtil severityUtil;
    @KafkaListener(topics = "logs-topic", groupId = "incident-group")
    public void consume(@Payload LogEvent logEvent){
        String key=redisService.generateKey(logEvent);
        long count =redisService.incrementCount(key);
        String severity = severityUtil.getSeverity(count);
        log.info("Log event count for Service {}: {}, severity: {}", logEvent.getService(), count, severity);
        if(redisService.isDuplicate(key)){
            log.info("Duplicate log event detected, skipping: {}", logEvent);
            return;
        }

        try {
            log.info("Received Logs: {}", logEvent);
            incidentClient.sendToIncidentService(logEvent);
        } catch (Exception e) {
            log.error("Error processing log event: {}", logEvent, e);
        }
    }
}
