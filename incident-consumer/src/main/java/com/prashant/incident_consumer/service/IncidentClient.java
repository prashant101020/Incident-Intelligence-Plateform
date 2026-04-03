package com.prashant.incident_consumer.service;

import com.prashant.incident_consumer.model.LogEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class IncidentClient {
    private final RestTemplate restTemplate = new RestTemplate();

    public void sendToIncidentService(LogEvent logEvent) {
        try {
            String url = "http://localhost:8080/api/incidents";

            // Convert LogEvent to Incident-compatible object
            IncidentRequest incident = new IncidentRequest();
            incident.setService(logEvent.getService());
            incident.setLevel(logEvent.getLevel());
            incident.setMessage(logEvent.getMessage());
            incident.setTimestamp(logEvent.getTimestamp());

            log.info("Sending incident to service: {}", incident);
            restTemplate.postForObject(url, incident, String.class);
            log.info("Successfully sent incident to service");
        } catch (Exception e) {
            log.error("Failed to send incident to service: {}", logEvent, e);
        }
    }

    // Inner class to match Incident model structure
    private static class IncidentRequest {
        private String service;
        private String level;
        private String message;
        private String timestamp;

        // Getters and setters
        public String getService() { return service; }
        public void setService(String service) { this.service = service; }
        public String getLevel() { return level; }
        public void setLevel(String level) { this.level = level; }
        public String getMessage() { return message; }
        public void setMessage(String message) { this.message = message; }
        public String getTimestamp() { return timestamp; }
        public void setTimestamp(String timestamp) { this.timestamp = timestamp; }

        @Override
        public String toString() {
            return "IncidentRequest{service='" + service + "', level='" + level + "', message='" + message + "', timestamp='" + timestamp + "'}";
        }
    }
}
