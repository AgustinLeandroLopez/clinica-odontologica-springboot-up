package com.ClinicaOdontologica.UP.service;

import com.ClinicaOdontologica.UP.dto.TurnoDTO;
import com.ClinicaOdontologica.UP.entity.Odontologo;
import com.ClinicaOdontologica.UP.entity.Paciente;
import com.ClinicaOdontologica.UP.entity.Turno;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ClinicaOdontologica.UP.repository.TurnoRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class TurnoService {
    @Autowired
    private TurnoRepository turnoRepository;

    public TurnoDTO guardarTurno(Turno turno) {

        // Convertir de DTO a entidad
        Turno turnoAux = new Turno();
        turnoAux.setFecha(turnoAux.getFecha());

        // Asignar IDs relacionados (opcional: validar que existan)
        Paciente paciente = new Paciente();
        paciente.setId(turnoAux.getPaciente().getId());

        Odontologo odontologo = new Odontologo();
        odontologo.setId(turnoAux.getOdontologo().getId());

        turno.setPaciente(paciente);
        turno.setOdontologo(odontologo);

        // Guardar en la base
        Turno turnoGuardado = turnoRepository.save(turno);

        // Convertir de vuelta a DTO para devolver
        return turnoATurnoDTO(turnoGuardado);
    }

    private TurnoDTO turnoATurnoDTO(Turno turno) {
        TurnoDTO turnoDTO = new TurnoDTO();
        turnoDTO.setId(turnoDTO.getId());
        turnoDTO.setPacienteId(turno.getPaciente().getId());
        turnoDTO.setOdontologoId(turno.getOdontologo().getId());
        turnoDTO.setFecha((turno.getFecha()));
        return turnoDTO;
    }

    public List<TurnoDTO> listarTurnos(){
        List<Turno> turnosList = turnoRepository.findAll();
        List<TurnoDTO> listaTurnoDTO = new ArrayList<>();

        //si poner Iger te crea automaticamnete esta lógica
        for (Turno turno : turnosList) {
            listaTurnoDTO.add(turnoATurnoDTO(turno));
        }
        return listaTurnoDTO;
    }
}
