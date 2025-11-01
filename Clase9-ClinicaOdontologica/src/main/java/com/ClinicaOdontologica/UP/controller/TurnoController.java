package com.ClinicaOdontologica.UP.controller;

import com.ClinicaOdontologica.UP.dto.TurnoDTO;
import com.ClinicaOdontologica.UP.entity.Odontologo;
import com.ClinicaOdontologica.UP.entity.Paciente;
import com.ClinicaOdontologica.UP.entity.Turno;
import com.ClinicaOdontologica.UP.exception.ResourceNotFoundException;
import com.ClinicaOdontologica.UP.exception.ResourceValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.ClinicaOdontologica.UP.service.OdontologoService;
import com.ClinicaOdontologica.UP.service.PacienteService;
import com.ClinicaOdontologica.UP.service.TurnoService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/turno")
public class TurnoController {
    private OdontologoService odontologoService;
    private PacienteService pacienteService;
    private TurnoService turnoService;

    @Autowired
    public TurnoController(OdontologoService odontologoService, PacienteService pacienteService, TurnoService turnoService) {
        this.odontologoService = odontologoService;
        this.pacienteService = pacienteService;
        this.turnoService = turnoService;
    }

    @PostMapping
    public ResponseEntity<TurnoDTO> registrarTurno(@RequestBody Turno turno)
            throws ResourceValidationException, ResourceNotFoundException {

        // Validar existencia de paciente y odontólogo antes de guardar
        Optional<Paciente> pacienteBuscado = pacienteService.buscarPacientePorId(turno.getPaciente().getId());
        Optional<Odontologo> odontologoBuscado = odontologoService.buscarOdontologoPorId(turno.getOdontologo().getId());

        if (pacienteBuscado.isEmpty()) {
            throw new ResourceNotFoundException("No se encontró el paciente con ID: " + turno.getPaciente().getId());
        }

        if (odontologoBuscado.isEmpty()) {
            throw new ResourceNotFoundException("No se encontró el odontólogo con ID: " + turno.getOdontologo().getId());
        }

        // Registrar turno
        TurnoDTO turnoGuardado = turnoService.guardarTurno(turno);
        return ResponseEntity.status(HttpStatus.CREATED).body(turnoGuardado);
    }

    @GetMapping
    public ResponseEntity<List<TurnoDTO>> listarTurnosDTO() throws ResourceNotFoundException {
        List<TurnoDTO> turnos = turnoService.listarTurnos();

        if (turnos.isEmpty()) {
            throw new ResourceNotFoundException("No se encontraron turnos registrados");
        }

        return ResponseEntity.ok(turnos);
    }
}
