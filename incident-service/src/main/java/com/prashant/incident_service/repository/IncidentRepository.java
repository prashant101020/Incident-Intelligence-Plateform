package com.prashant.incident_service.repository;

import com.prashant.incident_service.model.Incident;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IncidentRepository extends JpaRepository<Incident, Long> {
}
