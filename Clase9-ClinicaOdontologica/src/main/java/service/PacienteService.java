package service;

import dao.iDao;
import entity.Paciente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.PacienteRepository;

import java.util.List;

@Service
public class PacienteService {

    @Autowired
    private PacienteRepository pacienteRepository;


    public Paciente guardarPaciente(Paciente paciente){

        return pacienteRepository.save(paciente);
    }
    public Paciente buscarPacientePorId(Integer id){

        return pacienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Paciente no encontrado con id: " + id));
    }

    public List<Paciente> buscarPacientes(){

        return pacienteRepository.findAll();
    }

    public void actualizarPaciente(Paciente paciente){

        // Buscar el paciente existente
        Paciente pacienteBuscar = pacienteRepository.findById(paciente.getId())
                .orElseThrow(() -> new RuntimeException("Paciente no encontrado con id: " + paciente.getId()));


        // Actualizar solo los campos permitidos
        paciente.setNombre(paciente.getNombre());
        paciente.setApellido(paciente.getApellido());
        paciente.setNumeroContacto(paciente.getNumeroContacto());
        paciente.setEmail(paciente.getEmail());

        pacienteRepository.save(paciente);


    }

    public void  eliminarPaciente(Integer id){

        pacienteRepository.deleteById(id);
    }
}
