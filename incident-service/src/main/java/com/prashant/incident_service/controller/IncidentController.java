package com.prashant.incident_service.controller;

import com.prashant.incident_service.model.Incident;
import com.prashant.incident_service.services.IncidentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/incidents")
@RequiredArgsConstructor
@Slf4j
public class IncidentController {
    private final IncidentService incidentService;

    @GetMapping
    public List<Incident> getAllIncidents() {
        log.info("Fetching all incidents");
        List<Incident> incidents = incidentService.getAll();
        log.info("Retrieved {} incidents", incidents.size());
        return incidents;
    }

    @PostMapping
    public void createIncident(@RequestBody Incident incident) {
        log.info("Received incident: {}", incident);
        if (incident == null) {
            log.error("Received null incident");
            return;
        }
        try {
            incidentService.save(incident);
            log.info("Successfully saved incident: {}", incident);
        } catch (Exception e) {
            log.error("Failed to save incident: {}", incident, e);
        }
    }
}
