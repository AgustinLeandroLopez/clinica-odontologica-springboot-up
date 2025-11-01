package com.ClinicaOdontologica.UP.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class PacienteListaDTO {
    //DTO para listar datos del paciente
    private Integer id;
    private String nombre;
    private String apellido;
    private String email;
}
