package com.ClinicaOdontologica.UP.controller;

import com.ClinicaOdontologica.UP.dto.TurnoDTO;
import com.ClinicaOdontologica.UP.dto.TurnoUpdateDTO;
import com.ClinicaOdontologica.UP.entity.Odontologo;
import com.ClinicaOdontologica.UP.entity.Paciente;
import com.ClinicaOdontologica.UP.entity.Turno;
import com.ClinicaOdontologica.UP.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.ClinicaOdontologica.UP.service.OdontologoService;
import com.ClinicaOdontologica.UP.service.PacienteService;
import com.ClinicaOdontologica.UP.service.TurnoService;

import java.util.*;

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
    public ResponseEntity<Map<String, Object>> registrarTurno(@RequestBody Turno turno)
            throws ResourceNotFoundException {

        // Validar existencia de paciente y odontólogo antes de guardar
        validarExistenciaDePacienteYOdontologo(turno);

        // Registrar turno
        TurnoDTO turnoGuardado = turnoService.guardarTurno(turno);
        Map<String, Object> respuesta = new LinkedHashMap<>();
        respuesta.put("mensaje", "Turno registrado correctamente.");
        respuesta.put("turno", turnoGuardado);

        return ResponseEntity.status(HttpStatus.CREATED).body(respuesta);
    }

    @GetMapping
    public ResponseEntity<List<TurnoDTO>> listarTurnosDTO() throws ResourceNotFoundException {
        List<TurnoDTO> turnos = turnoService.listarTurnos();

        if (turnos.isEmpty()) {
            throw new ResourceNotFoundException("No se encontraron turnos registrados");
        }

        return ResponseEntity.ok(turnos);
    }

    //Diferenciamos las rutas por las busquedas por ID
    @GetMapping("/id/{id}")
    public ResponseEntity<TurnoDTO> buscarTurnoPorId(@PathVariable Integer id)
            throws ResourceNotFoundException{
        Optional<TurnoDTO> turnoDTOBuscado = turnoService.buscarTurnoDTOPorId(id);
        if (turnoDTOBuscado.isPresent()) {
            return ResponseEntity.ok(turnoDTOBuscado.get());
        } else {
            throw new ResourceNotFoundException("Turno no encontrado con id: " + id);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>>  eliminarTurno(@PathVariable Integer id)
            throws ResourceNotFoundException {

        // Verificar si existe antes de eliminar
        Optional<Turno> turnoBuscado = turnoService.buscarTurnoPorId(id);

        if (turnoBuscado.isPresent()) {
            turnoService.eliminarTurno(id);

            // Armar respuesta ordenada (LinkedHashMap)
            Map<String, Object> response = new LinkedHashMap<>();
            response.put("mensaje", "Exitoso - Turno eliminado correctamente id " +id);

            return ResponseEntity.ok(response);
        } else {
            throw new ResourceNotFoundException("No existe el turno con id " + id + " para eliminar");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> actualizarTurno(
            @PathVariable Integer id,
            @RequestBody TurnoUpdateDTO turnoActualizarDTO) throws ResourceNotFoundException {


        // Buscar el turno existente
        Optional <Turno> turnoBuscado = turnoService.buscarTurnoPorId(id);

        //Buscar Odontologo
        Optional<Odontologo> odontologoBuscado = validarExistenciaDeOdontologo(turnoActualizarDTO);

        if  (turnoBuscado.isPresent()) {
            Turno turno = turnoBuscado.get();

            // Guardar copia del anterior (sin modificarlo)
            Turno turnoAnterior = new Turno();
            turnoAnterior.setId(turno.getId());
            turnoAnterior.setPaciente(turno.getPaciente());
            turnoAnterior.setOdontologo(turno.getOdontologo());
            turnoAnterior.setFecha(turno.getFecha());

            // Actualizar solo los campos que se permiten
            turno.setFecha(turnoActualizarDTO.getFecha());
            turno.setOdontologo(odontologoBuscado.get());

            // Actualizar Turno
            TurnoDTO turnoGuardado = turnoService.actualizarTurno(turno);

            // Armar respuesta ordenada
            Map<String, Object> response = new LinkedHashMap<>();
            response.put("mensaje", "Exitoso - Turno id: " +id+" actualizado correctamente");
            response.put("turnoAnterior", turnoAnterior);
            response.put("turnoActualizado", turnoGuardado);

            return ResponseEntity.ok(response);

        } else {
            // Lanzar excepción personalizada si no se encuentra
            throw new ResourceNotFoundException("Turno no encontrado con id: " + id);
        }
    }

    //Busca solo odontologo porque usa el DTO del turno que ya trae un paciente existente
    private Optional<Odontologo> validarExistenciaDeOdontologo(TurnoUpdateDTO turnoUpdateDTO)
            throws ResourceNotFoundException {

        Optional<Odontologo> odontologoBuscado =
                odontologoService.buscarOdontologoPorId(turnoUpdateDTO.getOdontologoId());

        if (odontologoBuscado.isEmpty()) {
            throw new ResourceNotFoundException(
                    "No se encontró el odontólogo con ID: " + turnoUpdateDTO.getOdontologoId());
        }
        return  odontologoBuscado;
    }

    //Metodo para validar existencia de Paciente y Odontologo
    private void validarExistenciaDePacienteYOdontologo(Turno turno)
            throws ResourceNotFoundException {

        Optional<Paciente> pacienteBuscado =
                pacienteService.buscarPacientePorId(turno.getPaciente().getId());

        if (pacienteBuscado.isEmpty()) {
            throw new ResourceNotFoundException(
                    "No se encontró el paciente con ID: " + turno.getPaciente().getId());
        }

        Optional<Odontologo> odontologoBuscado =
                odontologoService.buscarOdontologoPorId(turno.getOdontologo().getId());

        if (odontologoBuscado.isEmpty()) {
            throw new ResourceNotFoundException(
                    "No se encontró el odontólogo con ID: " + turno.getOdontologo().getId());
        }
    }



}
