package br.eng.eaa.scheduling.repository;


import br.eng.eaa.scheduling.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<Appointment> findByPatientName(String patientName);
}
