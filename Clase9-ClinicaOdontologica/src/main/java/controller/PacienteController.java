package controller;

import entity.Paciente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.PacienteService;

import java.util.List;

@RestController
@RequestMapping("/paciente")
public class PacienteController {
    @Autowired
    private PacienteService pacienteService;

    @PostMapping
    public ResponseEntity<Paciente> registrarPaciente(@RequestBody  Paciente paciente){
        return ResponseEntity.ok(pacienteService.guardarPaciente(paciente));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Paciente> buscarPacientePorId(@PathVariable Integer id) {
        Paciente paciente = pacienteService.buscarPacientePorId(id);
        return ResponseEntity.ok(paciente);
    }

    @GetMapping
    public ResponseEntity<List<Paciente>> listarPacientes() {
        List<Paciente> pacientes = pacienteService.buscarPacientes();
        return ResponseEntity.ok(pacientes);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Paciente> actualizarPaciente(@PathVariable Integer id, @RequestBody Paciente pacienteActualizado) {

        // Buscar el paciente existente
        Paciente pacienteExistente = pacienteService.buscarPacientePorId(id);

        // Actualizar solo los campos que se permiten
        pacienteExistente.setNombre(pacienteActualizado.getNombre());
        pacienteExistente.setApellido(pacienteActualizado.getApellido());
        pacienteExistente.setNumeroContacto(pacienteActualizado.getNumeroContacto());
        pacienteExistente.setEmail(pacienteActualizado.getEmail());

        // Guardar cambios
        Paciente actualizado = pacienteService.guardarPaciente(pacienteExistente);

        return ResponseEntity.ok(actualizado);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarPaciente(@PathVariable Integer id) {
        pacienteService.eliminarPaciente(id);
        return ResponseEntity.ok("Paciente eliminado" + id);
    }

}

