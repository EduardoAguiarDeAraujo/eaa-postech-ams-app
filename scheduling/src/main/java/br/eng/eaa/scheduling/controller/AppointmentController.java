package br.eng.eaa.scheduling.controller;

import br.eng.eaa.scheduling.model.Appointment;
import br.eng.eaa.scheduling.service.AppointmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/appointments")
public class AppointmentController {

    @Autowired
    private AppointmentService service;

    @Operation(summary = "Criar uma nova consulta", description = "Registra uma nova consulta médica. Apenas médicos e enfermeiros podem criar.")
    @ApiResponse(responseCode = "200", description = "Consulta criada com sucesso")
    @ApiResponse(responseCode = "403", description = "Acesso negado - apenas médicos e enfermeiros podem criar")
    @PostMapping
    @PreAuthorize("hasAnyRole('MEDICO', 'ENFERMEIRO')")
    public Appointment create(@RequestBody Appointment appointment) {
        return service.createAppointment(appointment);
    }

    @Operation(summary = "Atualizar uma consulta existente", description = "Atualiza os detalhes de uma consulta existente. Apenas médicos podem editar.")
    @ApiResponse(responseCode = "200", description = "Consulta atualizada com sucesso")
    @ApiResponse(responseCode = "403", description = "Acesso negado - apenas médicos podem editar")
    @ApiResponse(responseCode = "404", description = "Consulta não encontrada")
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('MEDICO')") // Apenas médicos podem editar
    public Appointment update(@PathVariable Long id, @RequestBody Appointment appointment) {
        return service.updateAppointment(id, appointment);
    }

    @Operation(summary = "Listar consultas de um paciente", description = "Retorna as consultas de um paciente, filtrando por futuras se especificado.")
    @ApiResponse(responseCode = "200", description = "Lista de consultas retornada com sucesso")
    @GetMapping("/patient/{name}")
    @PreAuthorize("hasAnyRole('MEDICO', 'ENFERMEIRO', 'PACIENTE')")
    public List<Appointment> getForPatient(@PathVariable String name, @RequestParam(defaultValue = "false") boolean futureOnly) {
        return service.getAppointmentsForPatient(name, futureOnly);
    }
}