package com.prashant.incident_consumer.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class OpenAIRequest {
    private String model;
    private List<Message> messages;
}