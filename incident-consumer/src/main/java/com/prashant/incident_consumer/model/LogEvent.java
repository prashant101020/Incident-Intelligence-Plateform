package com.prashant.incident_consumer.model;

import lombok.Data;

@Data
public class LogEvent {
    private String service;
    private String level;
    private String message;
    private String timestamp;
}
