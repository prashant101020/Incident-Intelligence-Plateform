package com.prashant.incident_consumer.model;

import lombok.Data;

import java.util.List;

@Data
public class OpenAIResponse {
    private List<Choice> choices;

    @Data
    public static class Choice {
        private Message message;
    }
}