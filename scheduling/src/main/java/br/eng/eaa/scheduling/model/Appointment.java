package br.eng.eaa.scheduling.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
public class Appointment {
    @Id
    @GeneratedValue
    private Long id;
    private String patientName;
    private String doctorName;
    private LocalDateTime dateTime;
    private String description;
    private String status; // e.g., "SCHEDULED", "COMPLETED"
}