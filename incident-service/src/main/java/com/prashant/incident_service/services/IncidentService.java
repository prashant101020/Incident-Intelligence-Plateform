package com.prashant.incident_service.services;

import com.prashant.incident_service.model.Incident;
import com.prashant.incident_service.repository.IncidentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class IncidentService {
    private final IncidentRepository incidentRepository;

    public Incident save(Incident incident) {
        return incidentRepository.save(incident);
    }

    public Incident getById(long id) {
        return incidentRepository.findById(id).orElse(null);
    }

    public List<Incident> getAll() {
        return incidentRepository.findAll();
    }

}
