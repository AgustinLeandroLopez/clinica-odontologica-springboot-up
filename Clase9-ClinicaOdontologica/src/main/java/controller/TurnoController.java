package controller;

import dto.TurnoDTO;
import entity.Odontologo;
import entity.Paciente;
import entity.Turno;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import service.OdontologoService;
import service.PacienteService;
import service.TurnoService;

import java.util.Optional;

@RestController
@RequestMapping("/turno")
public class TurnoController {
    private OdontologoService odontologoService;
    private PacienteService pacienteService;
    private TurnoService turnoService;

    public TurnoController(OdontologoService odontologoService, PacienteService pacienteService, TurnoService turnoService) {
        this.odontologoService = odontologoService;
        this.pacienteService = pacienteService;
        this.turnoService = turnoService;
    }

    @PostMapping
    public ResponseEntity<TurnoDTO> registrarTurno(@RequestBody Turno turno){
        //Optional<Paciente> pacienteBuscado = pacienteService.buscarPacientePorId(turno.getPaciente().getId());
        //Optional<Odontologo> odontoloBuscado = odontologoService.buscarOdontologoPorId(turno.getOdontologo().getId());
        //Ya manejamos los Optional dentro del Service

        Paciente pacienteBuscado = pacienteService.buscarPacientePorId(turno.getPaciente().getId());
        Odontologo odontologoBuscado = odontologoService.buscarOdontologoPorId(turno.getOdontologo().getId());

        if(pacienteBuscado != null && odontologoBuscado != null){
            return ResponseEntity.ok(turnoService.guardarTurno(turno));
        }else{
            return ResponseEntity.badRequest().build();
        }

    }

}
