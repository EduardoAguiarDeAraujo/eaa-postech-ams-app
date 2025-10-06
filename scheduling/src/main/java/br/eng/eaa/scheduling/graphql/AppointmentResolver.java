package br.eng.eaa.scheduling.graphql;

import br.eng.eaa.scheduling.model.Appointment;
import br.eng.eaa.scheduling.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class AppointmentResolver {

    @Autowired
    private AppointmentService service;

    @QueryMapping
    @PreAuthorize("hasAnyRole('MEDICO', 'ENFERMEIRO', 'PACIENTE')")
    public List<Appointment> appointmentsForPatient(@Argument String patientName, @Argument boolean futureOnly) {
        return service.getAppointmentsForPatient(patientName, futureOnly);
    }
}