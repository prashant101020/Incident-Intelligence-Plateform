package com.prashant.incident_consumer.consumer;

import com.prashant.incident_consumer.Util.SeverityUtil;
import com.prashant.incident_consumer.model.AIResponse;
import com.prashant.incident_consumer.model.LogEvent;
import com.prashant.incident_consumer.service.AIService;
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
    private final AIService aiService;
    @KafkaListener(topics = "logs-topic", groupId = "incident-group")
    public void consume(@Payload LogEvent logEvent){
        String key=redisService.generateKey(logEvent);

        if(redisService.isDuplicate(key)){
            log.info("Duplicate log event detected, skipping: {}", logEvent);
            return;
        }

        long count =redisService.incrementCount(key);

//        String AIAnalysis = aiService.analyzeLog(logEvent, count, severityUtil.getSeverity(count));
        log.info("Received Logs: {}", logEvent);
        String severity = severityUtil.getSeverity(count);
        AIResponse ai = aiService.analyze(logEvent, count, severity);

        log.info(ai.getRootCause());
        log.info(ai.getSuggestedFix());


        log.info("Log event count for Service {}: {}, severity: {}", logEvent.getService(), count, severity);
        log.info("AI Analysis for log event: {}, analysis: {}", logEvent, ai);



        try {

            incidentClient.sendToIncidentService(logEvent);
        } catch (Exception e) {
            log.error("Error processing log event: {}", logEvent, e);
        }
    }
}
