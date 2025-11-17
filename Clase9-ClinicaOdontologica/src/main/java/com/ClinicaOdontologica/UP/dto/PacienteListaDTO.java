package com.ClinicaOdontologica.UP.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter

public class PacienteListaDTO {
    //DTO para listar solo datos del paciente sin domicilio
    private Integer id;
    private String nombre;
    private String apellido;
    private Integer numeroContacto;
    private LocalDate fechaIngreso;
    private String email;
}
