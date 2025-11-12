package com.ClinicaOdontologica.UP.controller;

import com.ClinicaOdontologica.UP.entity.Odontologo;
import com.ClinicaOdontologica.UP.exception.ResourceNotFoundException;
import com.ClinicaOdontologica.UP.exception.ResourceValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.ClinicaOdontologica.UP.service.OdontologoService;

import java.util.*;

@RestController
@RequestMapping("/odontologo")
public class OdontologoController {
    @Autowired
    private OdontologoService odontologoService;

    @PostMapping
    public ResponseEntity<Map<String, Object>> registrarOdontologo(@RequestBody Odontologo odontologo)
            throws ResourceValidationException {

        // Validar si ya existe un odontólogo con la misma matrícula
        Optional<Odontologo> odontologoBuscado = odontologoService.buscarPorMatricula(odontologo.getMatricula());
        if (odontologoBuscado.isPresent()) {
            throw new ResourceValidationException("Ya existe un odontólogo registrado con matrícula: " + odontologo.getMatricula());
        }

        // Guardar y obtener el odontólogo creado
        Odontologo odontologoGuardado = odontologoService.guardarOdontologo(odontologo);

        // Devolver el objeto creado con 201 Created
        Map<String, Object> respuesta = new HashMap<>();
        respuesta.put("mensaje", "Exitoso -  Alta de odontólogo creada correctamente.");
        respuesta.put("odontologo", odontologoGuardado);

        return ResponseEntity.status(HttpStatus.CREATED).body(respuesta);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Odontologo> buscarOdontologoPorId(@PathVariable Integer id)
            throws ResourceNotFoundException {

        Optional<Odontologo> odontologoBuscado = odontologoService.buscarOdontologoPorId(id);

        if (odontologoBuscado.isPresent()) {
            return ResponseEntity.ok(odontologoBuscado.get());
        } else {
            throw new ResourceNotFoundException("Odontólogo no encontrado con id: " + id);
        }
    }

    @GetMapping
    public ResponseEntity<List<Odontologo>> listarOdontologos() throws ResourceNotFoundException {
        List<Odontologo> odontologos = odontologoService.buscarOdontologos();

        if (odontologos.isEmpty()) {
            throw new ResourceNotFoundException("No se encontraron odontólogos registrados");
        }

        return ResponseEntity.ok(odontologos);
    }


    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> actualizarOdontologo(
            @PathVariable Integer id,
            @RequestBody Odontologo odontologoActualizar) throws ResourceNotFoundException {

        // Buscar el odontologo existente
        Optional <Odontologo> odontologoBuscado = odontologoService.buscarOdontologoPorId(id);

        if  (odontologoBuscado.isPresent()) {
            Odontologo odontologo = odontologoBuscado.get();

            // Guardar copia del anterior (sin modificarlo)
            Odontologo odontologoAnterior = new Odontologo();
            odontologoAnterior.setId(odontologo.getId());
            odontologoAnterior.setNombre(odontologo.getNombre());
            odontologoAnterior.setApellido(odontologo.getApellido());
            odontologoAnterior.setMatricula(odontologo.getMatricula());

            // Actualizar solo los campos que se permiten
            odontologo.setNombre(odontologoActualizar.getNombre());
            odontologo.setApellido(odontologoActualizar.getApellido());

            // Guardar y obtener el odontólogo creado
            Odontologo odontologoGuardado = odontologoService.guardarOdontologo(odontologo);

            // Armar respuesta ordenada
            Map<String, Object> response = new LinkedHashMap<>();
            response.put("mensaje", "Exitoso - Odontólogo "+id+" actualizado correctamente");
            response.put("odontologoAnterior", odontologoAnterior);
            response.put("odontologoActualizado", odontologoGuardado);

            return ResponseEntity.ok(response);

        } else {
            // Lanzar excepción personalizada si no se encuentra
            throw new ResourceNotFoundException("Odontologo no encontrado con id: " + id);
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> eliminarOdontologo(@PathVariable Integer id)
            throws ResourceNotFoundException {

        // Verificar si existe antes de eliminar
        Optional<Odontologo> odontologoBuscado = odontologoService.buscarOdontologoPorId(id);

        if (odontologoBuscado.isPresent()) {
            odontologoService.eliminarOdontologo(id);

            // Armar respuesta ordenada (LinkedHashMap)
            Map<String, Object> response = new LinkedHashMap<>();
            response.put("mensaje", "Exitoso - Odontólogo eliminado correctamente id " +id);

            return ResponseEntity.ok(response);
        } else {
            throw new ResourceNotFoundException("No existe el odontologo con id " + id + " para eliminar");
        }
    }

}
