package br.eng.eaa.scheduling.service;

import br.eng.eaa.scheduling.config.RabbitMQConfig;
import br.eng.eaa.scheduling.model.Appointment;
import br.eng.eaa.scheduling.repository.AppointmentRepository;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository repository;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @PreAuthorize("hasAnyRole('MEDICO', 'ENFERMEIRO')")
    public Appointment createAppointment(Appointment appointment) {
        Appointment saved = repository.save(appointment);
        // Envia mensagem assíncrona para notificações
        rabbitTemplate.convertAndSend(RabbitMQConfig.APPOINTMENT_QUEUE, "Nova consulta criada: " + saved.getId());
        return saved;
    }

    @PreAuthorize("hasRole('MEDICO')")
    public Appointment updateAppointment(Long id, Appointment updated) {
        Appointment existing = repository.findById(id).orElseThrow();
        existing.setDateTime(updated.getDateTime());
        existing.setDescription(updated.getDescription());
        existing.setStatus(updated.getStatus());
        Appointment saved = repository.save(existing);
        // Envia mensagem assíncrona
        rabbitTemplate.convertAndSend(RabbitMQConfig.APPOINTMENT_QUEUE, "Consulta atualizada: " + saved.getId());
        return saved;
    }

    @PreAuthorize("hasAnyRole('MEDICO', 'ENFERMEIRO', 'PACIENTE')")
    public List<Appointment> getAppointmentsForPatient(String patientName, boolean futureOnly) {
        // Lógica simplificada; para paciente, assume username == patientName
        // Em produção, integrar com user context
        List<Appointment> appointments = repository.findByPatientName(patientName);
        if (futureOnly) {
            return appointments.stream().filter(a -> a.getDateTime().isAfter(LocalDateTime.now())).toList();
        }
        return appointments;
    }
}
