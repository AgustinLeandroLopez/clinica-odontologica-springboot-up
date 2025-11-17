package com.ClinicaOdontologica.UP.service;

import com.ClinicaOdontologica.UP.dto.TurnoDTO;
import com.ClinicaOdontologica.UP.entity.Domicilio;
import com.ClinicaOdontologica.UP.entity.Odontologo;
import com.ClinicaOdontologica.UP.entity.Paciente;
import com.ClinicaOdontologica.UP.entity.Turno;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TurnoServiceTest {
    @Autowired
    TurnoService turnoService;
    @Autowired
    OdontologoService odontologoService;
    @Autowired
    PacienteService pacienteService;

    @Test
    @Order(1)
    public void guardarTurno() {
        // DADO - Debo tener un paciente y un odontologo existente
        Domicilio domicilio = new Domicilio("Calle Falsa", 123, "CABA", "Buenos Aires");
        Paciente paciente = new Paciente("Agustín","Lopez",12345678,LocalDate.now(),domicilio,"agustin@pruebatest.com");
        Paciente pacienteGuardado = pacienteService.guardarPaciente(paciente);

        Odontologo odontologo = new Odontologo("Jose","Maria",1234);
        Odontologo odontologoGuardado = odontologoService.guardarOdontologo(odontologo);

        // CUANDO - Creo un turno válido
        Turno turno = new Turno(pacienteGuardado,odontologoGuardado,LocalDate.of(2025, 11, 22));
        TurnoDTO turnoDTOGuardado = turnoService.guardarTurno(turno);
        System.out.println("Turno guardado: " + turnoDTOGuardado);
        // ENTONCES - Debe haberse guardado con ID asignado
        assertNotNull(turnoDTOGuardado.getId());
    }


    @Test
    @Order(2)
    public void buscarTurnoPorId() {
        // DADO - Tengo un ID para buscar
        int id = 1;

        // CUANDO - Tengo el ID turno para buscar
        Optional<Turno> turnoBuscado= turnoService.buscarTurnoPorId(id);
        System.out.println("datos encontrados: " + turnoBuscado);

        // ENTONCES - Debe existir
        assertEquals(id, turnoBuscado.get().getId());
    }

    @Test
    @Order(3)
    public void listarTurnos() {
        // DADO -
        // CUANDO -
        List<TurnoDTO> turnosDTO = turnoService.listarTurnos();
        // ENTONCES -
        assertNotNull(turnosDTO, "La lista no debe ser null");
        assertFalse(turnosDTO.isEmpty(), "Debe haber al menos un turno");

        System.out.println("Total turnos: " + turnosDTO.size());
        turnosDTO.forEach(System.out::println);

        //Validamos sobre el @order(1)
        assertTrue(turnosDTO.stream()
                .anyMatch(p -> p.getId().equals(1)));
    }

    @Test
    @Order(4)
    public void actualizarTurno() {
        // DADO - Quiero actualizar los datos de un turno
        int id = 1;
        Optional<Turno> turnoBuscado = turnoService.buscarTurnoPorId(id);
        assertTrue(turnoBuscado.isPresent(), "Debe existir el turno con "+ id +" para esta prueba");

        Turno turno = turnoBuscado.get();
        // Creo un nuevo odontologo para asignarlo al turno
        Odontologo nuevoOdontologo = new Odontologo("Juan","Roman",99999);
        Odontologo odontologoGuardado = odontologoService.guardarOdontologo(nuevoOdontologo);

        // Nueva fecha a actualizar
        LocalDate nuevaFecha = LocalDate.of(2025, 12, 1);

        // CUANDO - Actualizo el turno
        turno.setFecha(nuevaFecha);
        turno.setOdontologo(odontologoGuardado);
        TurnoDTO turnoDTOGuardado = turnoService.actualizarTurno(turno);

        // ENTONCES - Verifico usando el DTO
        assertNotNull(turnoDTOGuardado.getId());
        assertEquals(nuevaFecha, turnoDTOGuardado.getFecha());
        assertEquals(odontologoGuardado.getId(), turnoDTOGuardado.getOdontologoId());
    }

    @Test
    @Order(5)
    public void eliminarTurno() {
        //DADO - Quiero eliminar un turno
        int id = 1;
        Optional<Turno> turnoBuscado = turnoService.buscarTurnoPorId(id);
        assertTrue(turnoBuscado.isPresent(), "Debe existir el turno " +id+" para esta prueba");

        Turno turno = turnoBuscado.get();
        System.out.println("Antes: " + turno);

        //CUANDO - Se nos solicita eliminarlo
        turnoService.eliminarTurno(turno.getId());

        //ENOTNCES - Verificamos
        Optional<Turno> turnoEliminado = turnoService.buscarTurnoPorId(id);
        assertTrue(turnoEliminado.isEmpty(), "El turno "+ id +"debería haber sido eliminado");

        System.out.println("Turno con " + id + " eliminado correctamente");
    }
}
