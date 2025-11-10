package com.ClinicaOdontologica.UP.controller;

import com.ClinicaOdontologica.UP.dto.PacienteDTO;
import com.ClinicaOdontologica.UP.dto.PacienteListaDTO;
import com.ClinicaOdontologica.UP.entity.Paciente;
import com.ClinicaOdontologica.UP.exception.ResourceNotFoundException;
import com.ClinicaOdontologica.UP.exception.ResourceValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.ClinicaOdontologica.UP.service.PacienteService;

import java.util.*;

@RestController
@RequestMapping("/paciente")
public class PacienteController {
    @Autowired
    private PacienteService pacienteService;

    @PostMapping
    public ResponseEntity<Map<String, Object>> registrarPaciente(@RequestBody Paciente paciente)
            throws ResourceValidationException {

        // Verificar si ya existe un paciente con el mismo email
        Optional<Paciente> existente = pacienteService.buscarPorEmail(paciente.getEmail());
        if (existente.isPresent()) {
            throw new ResourceValidationException("Ya existe un paciente registrado con el email: " + paciente.getEmail());
        }

        // Guardar el nuevo paciente
        Paciente pacienteGuardado = pacienteService.guardarPaciente(paciente);

        // Devolver el objeto creado con 201 Created
        Map<String, Object> respuesta = new HashMap<>();
        respuesta.put("mensaje", "Exitoso -  Alta de Paciente creada correctamente.");
        respuesta.put("paciente", pacienteGuardado);

        return ResponseEntity.status(HttpStatus.CREATED).body(respuesta);
    }

    //Diferenciamos las rutas por las busquedas por ID y Correo
    @GetMapping("/id/{id}")
    public ResponseEntity<Paciente> buscarPacientePorId(@PathVariable Integer id)
            throws ResourceNotFoundException{
        Optional<Paciente> pacienteBuscado = pacienteService.buscarPacientePorId(id);
        if (pacienteBuscado.isPresent()) {
            return ResponseEntity.ok(pacienteBuscado.get());
        } else {
            throw new ResourceNotFoundException("Paciente no encontrado con id: " + id);
        }
    }

    //Si consultamos correo solo devuelve datos del paciente especifico - Nombre y apellido
    @GetMapping("/email/{email}")
    public ResponseEntity<PacienteDTO> buscarPorEmail(@PathVariable String email) throws ResourceNotFoundException{
        Optional<PacienteDTO> pacienteDTOBuscado=pacienteService.buscarDTOporEmail(email);

        if(pacienteDTOBuscado.isPresent()){
            return ResponseEntity.ok(pacienteDTOBuscado.get());
        }
        else{
            throw new ResourceNotFoundException("Paciente no encontrado por el correo");
        }
    }


    //Lista los Pacientes para obtener el nombre, fecha de ingreso e ID - Si necesita mas detalles del paciente debe busar por ID
    @GetMapping
    public ResponseEntity<List<PacienteListaDTO>> listarPacientes() throws ResourceNotFoundException {
        List<PacienteListaDTO> pacientes = pacienteService.buscarPacientes();

        if (pacientes.isEmpty()) {
            throw new ResourceNotFoundException("No se encontraron pacientes registrados");
        }

        return ResponseEntity.ok(pacientes);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> actualizarPaciente(@PathVariable Integer id, @RequestBody Paciente pacienteActualizar)
            throws ResourceNotFoundException{

        // Buscar el paciente existente
        Optional <Paciente> pacienteBuscado = pacienteService.buscarPacientePorId(id);

        if  (pacienteBuscado.isPresent()) {
            Paciente paciente = pacienteBuscado.get();

            // Guardar copia del anterior (sin modificarlo)
            Paciente pacienteAnterior = new Paciente();
            pacienteAnterior.setId(paciente.getId());
            pacienteAnterior.setNombre(paciente.getNombre());
            pacienteAnterior.setApellido(paciente.getApellido());
            pacienteAnterior.setNumeroContacto(paciente.getNumeroContacto());
            pacienteAnterior.setFechaIngreso(paciente.getFechaIngreso());
            pacienteAnterior.setDomicilio(paciente.getDomicilio());
            pacienteAnterior.setEmail(paciente.getEmail());

            // Actualizar solo los campos que se permiten
            paciente.setNombre(pacienteActualizar.getNombre());
            paciente.setApellido(pacienteActualizar.getApellido());
            paciente.setNumeroContacto(pacienteActualizar.getNumeroContacto());
            paciente.setEmail(pacienteActualizar.getEmail());

            // Guardar y obtener el paciente creado
            Paciente pacienteGuardado = pacienteService.guardarPaciente(paciente);

            // Armar respuesta ordenada
            Map<String, Object> response = new LinkedHashMap<>();
            response.put("mensaje", "Exitoso - Paciente actualizado correctamente - Permite solo nombre, apellido, Numero y correo.");
            response.put("pacienteAnterior", pacienteAnterior);
            response.put("odontologoActualizado", pacienteGuardado);

            return ResponseEntity.ok(response);

        } else {
            // Lanzar excepción personalizada si no se encuentra
            throw new ResourceNotFoundException("Paciente no encontrado con id: " + id);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>>  eliminarPaciente(@PathVariable Integer id)
            throws ResourceNotFoundException {

        // Verificar si el paciente existe antes de eliminarlo
        Optional<Paciente> pacienteBuscado = pacienteService.buscarPacientePorId(id);

        if (pacienteBuscado.isPresent()) {
            pacienteService.eliminarPaciente(id);

            // Armar respuesta ordenada (LinkedHashMap)
            Map<String, Object> response = new LinkedHashMap<>();
            response.put("mensaje", "Exitoso - Paciente eliminado correctamente id " +id);

            return ResponseEntity.ok(response);
        } else {
            throw new ResourceNotFoundException("No existe el paciente con id " + id + " para eliminar");
        }
    }
}

