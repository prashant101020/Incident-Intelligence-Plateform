package com.prashant.incident_consumer.service;

import com.prashant.incident_consumer.model.*;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import tools.jackson.databind.ObjectMapper;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AIService {

    @org.springframework.beans.factory.annotation.Value("${ai.openai.api-key}")
    private String apiKey;

    @Value("${ai.openai.url}")
    private String url;

    private final RestTemplate restTemplate = new RestTemplate();

    public AIResponse analyze(LogEvent log, long count, String severity) {

        String prompt = buildPrompt(log, count, severity);

        OpenAIRequest request = new OpenAIRequest(
                "gpt-oss-120b",
                List.of(new Message("user", prompt))
        );

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(apiKey);
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("HTTP-Referer", "http://localhost:8081");
        headers.set("X-Title", "incident-intelligence");
        HttpEntity<OpenAIRequest> entity = new HttpEntity<>(request, headers);

        try {
            ResponseEntity<OpenAIResponse> response = restTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    entity,
                    OpenAIResponse.class
            );

            String content = response.getBody()
                    .getChoices()
                    .get(0)
                    .getMessage()
                    .getContent();

            return parseResponse(content);

        } catch (Exception e) {
            return new AIResponse("Unknown issue", "Manual investigation required");
        }
    }
    private String buildPrompt(LogEvent log, long count, String severity) {
        return """
        Analyze the following system error log and provide:

        1. Root Cause
        2. Suggested Fix

        Log Details:
        Service: %s
        Error: %s
        Frequency: %d
        Severity: %s

        Respond strictly in JSON:
        {
          "rootCause": "...",
          "suggestedFix": "..."
        }
        """.formatted(
                log.getService(),
                log.getMessage(),
                count,
                severity
        );
    }
    private AIResponse parseResponse(String content) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(content, AIResponse.class);
        } catch (Exception e) {
            return new AIResponse("Parsing failed", "Check logs manually");
        }
    }
}