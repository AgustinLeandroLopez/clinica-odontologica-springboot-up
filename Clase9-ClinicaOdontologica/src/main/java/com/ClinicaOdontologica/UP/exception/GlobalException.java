package com.ClinicaOdontologica.UP.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalException  {
    //Toda la lóigca del manejo de la exception

    // Cuando no se encuentra un recurso:
    @ExceptionHandler({ResourceNotFoundException.class})
    public ResponseEntity<String> tratamientoRNFE(ResourceNotFoundException e){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    // Cuando hay conflicto de datos o validación
    @ExceptionHandler({ResourceValidationException.class})
    public ResponseEntity<String> tratamientoRVE(ResourceValidationException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
    }

    //Cuando ocurre cualquier otro error inesperado
    @ExceptionHandler({Exception.class})
    public ResponseEntity<String> tratamientoGeneral(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error interno del servidor: " + e.getMessage());
    }

}
