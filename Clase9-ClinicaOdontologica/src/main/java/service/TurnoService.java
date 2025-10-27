package service;

import dto.TurnoDTO;
import entity.Odontologo;
import entity.Paciente;
import entity.Turno;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.TurnoRepository;

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
}
