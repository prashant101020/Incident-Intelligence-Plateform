package com.prashant.incident_consumer.Util;

import org.springframework.stereotype.Component;

@Component
public class SeverityUtil {

    public String getSeverity(long count) {
        if(count>=10){
            return "HIGH";
        }
        if(count>=5){
            return "MEDIUM";
        }
            return "LOW";
    }
}
