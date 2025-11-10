package com.ClinicaOdontologica.UP.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalException {

    // Devolvemos siempre un JSON para el front

    // Cuando no se encuentra un recurso
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String, Object>> tratamientoRNFE(ResourceNotFoundException e) {
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("mensaje", e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    // Cuando hay conflicto de datos o validación
    @ExceptionHandler(ResourceValidationException.class)
    public ResponseEntity<Map<String, Object>> tratamientoRVE(ResourceValidationException e) {
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("mensaje", e.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    // Cuando ocurre cualquier otro error inesperado
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> tratamientoGeneral(Exception e) {
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("mensaje", "Error interno del servidor: " + e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }


    // Excepcion para cuando queremos eliminar un registro y existe en la base de datos de turnos
    @ExceptionHandler(org.springframework.dao.DataIntegrityViolationException.class)
    public ResponseEntity<Map<String, Object>> handleDataIntegrityViolation(Exception e) {
        Map<String, Object> response = new LinkedHashMap<>();
        String mensajeError = e.getMessage();

        if (mensajeError.contains("PACIENTE_ID")) {
            response.put("mensaje", "No se puede eliminar el paciente porque tiene turnos asignados.");
        } else if (mensajeError.contains("ODONTOLOGO_ID")) {
            response.put("mensaje", "No se puede eliminar el odontólogo porque tiene turnos asignados.");
        } else {
            response.put("mensaje", "No se puede eliminar el registro porque tiene datos relacionados.");
        }

        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

}
