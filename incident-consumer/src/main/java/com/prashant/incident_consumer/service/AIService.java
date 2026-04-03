package com.prashant.incident_consumer.service;

import com.prashant.incident_consumer.model.LogEvent;
import org.springframework.stereotype.Service;

@Service
public class AIService {
    public String analyzeLog(LogEvent log, long count, String severity) {
        // Placeholder for AI analysis logic
        // In a real implementation, this would call an AI model or service
        if (log.getMessage().contains("DB") || log.getMessage().contains("exception")) {
            return "Root cause: DB overload | Fix: Check connection pool / slow queries";
        } else if (log.getMessage().contains("timeout")) {
            return "Root cause: Service latency | Fix: Check downstream dependencies";
        }
      return  "Root cause: Unknown | Fix: Further investigation needed";
    }
}
