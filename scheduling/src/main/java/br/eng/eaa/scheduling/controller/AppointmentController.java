package br.eng.eaa.scheduling.controller;

import br.eng.eaa.scheduling.model.Appointment;
import br.eng.eaa.scheduling.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/appointments")
public class AppointmentController {

    @Autowired
    private AppointmentService service;

    @PostMapping
    public Appointment create(@RequestBody Appointment appointment) {
        return service.createAppointment(appointment);
    }

    @PutMapping("/{id}")
    public Appointment update(@PathVariable Long id, @RequestBody Appointment appointment) {
        return service.updateAppointment(id, appointment);
    }

    // Endpoint para histórico simples; GraphQL para queries avançadas
    @GetMapping("/patient/{name}")
    public List<Appointment> getForPatient(@PathVariable String name, @RequestParam(defaultValue = "false") boolean futureOnly) {
        return service.getAppointmentsForPatient(name, futureOnly);
    }
}
