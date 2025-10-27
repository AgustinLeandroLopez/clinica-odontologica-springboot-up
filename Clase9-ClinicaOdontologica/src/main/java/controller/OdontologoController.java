package controller;

import entity.Odontologo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.OdontologoService;

import java.util.List;

@RestController
@RequestMapping("/odontologo")
public class OdontologoController {
    @Autowired
    private OdontologoService odontologoService;

    @PostMapping
    public ResponseEntity<Odontologo> registrarOdontologo(@RequestBody Odontologo odontologo) {
        return ResponseEntity.ok(odontologoService.guardarOdontologo(odontologo));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Odontologo> buscarOdontologoPorId(@PathVariable Integer id) {
        Odontologo odontologo = odontologoService.buscarOdontologoPorId(id);
        return ResponseEntity.ok(odontologo);
    }

    @GetMapping
    public ResponseEntity<List<Odontologo>> listarOdontologos() {
        List<Odontologo> odontologos = odontologoService.buscarOdontologo();
        return ResponseEntity.ok(odontologos);
    }


    @PutMapping("/{id}")
    public ResponseEntity<Odontologo> actualizarOdontologo(
            @PathVariable Integer id,
            @RequestBody Odontologo odontologoActualizado) {

        // Buscamos el existente
        Odontologo odontologoExistente = odontologoService.buscarOdontologoPorId(id);

        // Actualizamos los campos que correspondan
        odontologoExistente.setNombre(odontologoActualizado.getNombre());
        odontologoExistente.setApellido(odontologoActualizado.getApellido());

        // Guardamos los cambios
        Odontologo actualizado = odontologoService.guardarOdontologo(odontologoExistente);
        return ResponseEntity.ok(actualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarOdontologo(@PathVariable Integer id) {
        odontologoService.eliminarOdontologo(id);
        return ResponseEntity.ok("Odontólogo eliminado correctamente (id: " + id + ")");
    }
}
