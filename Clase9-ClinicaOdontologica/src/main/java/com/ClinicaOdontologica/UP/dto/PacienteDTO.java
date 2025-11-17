package com.ClinicaOdontologica.UP.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PacienteDTO {
    //DTO para solo consultar datos del paciente especifico (nombre y apellido) - Objetivo: Para busquedas por Correo
    private String nombre;
    private String apellido;
}
