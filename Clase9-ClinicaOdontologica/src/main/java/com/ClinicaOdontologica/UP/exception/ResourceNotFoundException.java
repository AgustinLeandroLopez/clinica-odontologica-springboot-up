package com.ClinicaOdontologica.UP.exception;

public class ResourceNotFoundException extends Exception {
    //toda la logica del manejo de la exception
    public ResourceNotFoundException(String mensaje)
    {
        super(mensaje);
    }
}
