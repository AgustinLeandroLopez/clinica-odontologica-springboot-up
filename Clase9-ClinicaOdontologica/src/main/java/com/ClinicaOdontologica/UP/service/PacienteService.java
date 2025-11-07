package com.ClinicaOdontologica.UP.service;

import com.ClinicaOdontologica.UP.dto.PacienteDTO;
import com.ClinicaOdontologica.UP.dto.PacienteListaDTO;
import com.ClinicaOdontologica.UP.entity.Paciente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ClinicaOdontologica.UP.repository.PacienteRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PacienteService {

    @Autowired
    private PacienteRepository pacienteRepository;


    public Paciente guardarPaciente(Paciente paciente){

        return pacienteRepository.save(paciente);
    }

    public Optional<Paciente> buscarPacientePorId(Integer id){

        return pacienteRepository.findById(id);
    }

    public List<PacienteListaDTO> buscarPacientes(){

        List<Paciente> pacienteList =  pacienteRepository.findAll();
        List<PacienteListaDTO> pacienteListaDTO = new ArrayList<>();
        for (Paciente paciente : pacienteList){
            pacienteListaDTO.add(pacientesDTOLista(paciente));
        }
        return pacienteListaDTO;
    }

    public void actualizarPaciente(Paciente paciente){

        pacienteRepository.save(paciente);
    }

    public void  eliminarPaciente(Integer id){

        pacienteRepository.deleteById(id);
    }

    //Metodo básico
    public Optional<Paciente> buscarPorEmail(String email) {
        return pacienteRepository.findByEmail(email);
    }

    // Para busquedas por mail usamos el DTO por ""regla de negocio""
    public Optional<PacienteDTO> buscarDTOporEmail(String email) {
        Optional<Paciente> pacienteBuscado = pacienteRepository.findByEmail(email);

        if (pacienteBuscado.isPresent()) {
            Paciente pacienteAux = pacienteBuscado.get();
            PacienteDTO pacienteDTO = new PacienteDTO();
            pacienteDTO.setNombre(pacienteAux.getNombre());
            pacienteDTO.setApellido(pacienteAux.getApellido());
            return Optional.of(pacienteDTO);
        } else {
            return Optional.empty();
        }
    }

    private PacienteListaDTO pacientesDTOLista(Paciente paciente) {
        PacienteListaDTO pacienteListaDTO = new PacienteListaDTO();
        pacienteListaDTO.setId(paciente.getId());
        pacienteListaDTO.setNombre(paciente.getNombre());
        pacienteListaDTO.setApellido(paciente.getApellido());
        pacienteListaDTO.setNumeroContacto(paciente.getNumeroContacto());
        pacienteListaDTO.setFechaIngreso(paciente.getFechaIngreso());
        pacienteListaDTO.setEmail(paciente.getEmail());

        return pacienteListaDTO;
    }
}
