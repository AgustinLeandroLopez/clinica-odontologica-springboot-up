package com.ClinicaOdontologica.UP.service;

import com.ClinicaOdontologica.UP.dto.PacienteDTO;
import com.ClinicaOdontologica.UP.dto.TurnoDTO;
import com.ClinicaOdontologica.UP.entity.Paciente;
import com.ClinicaOdontologica.UP.entity.Turno;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ClinicaOdontologica.UP.repository.TurnoRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TurnoService {
    @Autowired
    private TurnoRepository turnoRepository;

    public TurnoDTO guardarTurno(Turno turno) {

        Turno turnoGuardado= turnoRepository.save(turno);
        return turnoATurnoDTO(turnoGuardado);
    }

    private TurnoDTO turnoATurnoDTO(Turno turno) {
        TurnoDTO turnoDTO = new TurnoDTO();
        turnoDTO.setId(turno.getId());
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

    public void eliminarTurno(Integer id){

        turnoRepository.deleteById(id);
    }

    public Optional<Turno> buscarTurnoPorId(Integer id) {
        return turnoRepository.findById(id);
    }

    // Buscamos turno DTO para devolver en el actualizar
    public Optional<TurnoDTO> buscarTurnoDTOPorId(Integer id) {

        Optional<Turno> turnoBuscado = turnoRepository.findById(id);

        if (turnoBuscado.isPresent()) {
            Turno turnoAux = turnoBuscado.get();
            TurnoDTO turnoDTO = new TurnoDTO();

            turnoDTO.setId(turnoAux.getId());
            turnoDTO.setFecha(turnoAux.getFecha());
            turnoDTO.setPacienteId(turnoAux.getPaciente().getId());
            turnoDTO.setOdontologoId(turnoAux.getOdontologo().getId());
            return Optional.of(turnoDTO);
        } else {
            return Optional.empty();
        }
    }
}
