package com.ClinicaOdontologica.UP.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class TurnoUpdateDTO {
    //DTO para actualizar sin solicitar el ID paciente

    private LocalDate fecha;
    private Integer odontologoId;

}
