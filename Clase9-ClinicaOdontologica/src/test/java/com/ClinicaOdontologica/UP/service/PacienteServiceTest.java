package com.ClinicaOdontologica.UP.service;

import com.ClinicaOdontologica.UP.dto.PacienteListaDTO;
import com.ClinicaOdontologica.UP.entity.Domicilio;
import com.ClinicaOdontologica.UP.entity.Paciente;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PacienteServiceTest {
    @Autowired
    PacienteService pacienteService;

    @Test
    @Order(1)
    public void guardarPaciente() {
        // DADO - Quiero guardar un paciente
        Paciente paciente = new Paciente(
                "Agustin",
                "Lopez",
                95959595,
                LocalDate.of(2025,11,14),
                new Domicilio("Calle prueba", 123,"Palermo","CABA"),
                "agustin@prueba.com"
        );
        // CUANDO - Guardo ese paciente
        Paciente pacienteGuardado = pacienteService.guardarPaciente(paciente);
        System.out.println("Paciente guardado: " + pacienteGuardado);
        // ENTONCES - Debe asignarse un ID
        assertNotNull(pacienteGuardado.getId());
    }

    @Test
    @Order(2)
    public void buscarPacientePorId() {
        // DADO - Tengo un ID para buscar
        int id = 1;
        // CUANDO - Tengo el ID Paciente para buscar
        Optional<Paciente> pacienteBuscado= pacienteService.buscarPacientePorId(id);
        System.out.println("datos encontrados: " + pacienteBuscado);
        // ENTONCES - Debe existir
        assertEquals(id, pacienteBuscado.get().getId());
    }

    @Test
    @Order(3)
    public void listarPacientes() {
        // DADO -
        // CUANDO -
        List<PacienteListaDTO> pacientesDTO = pacienteService.buscarPacientes();
        // ENTONCES -
        assertNotNull(pacientesDTO, "La lista no debe ser null");
        assertFalse(pacientesDTO.isEmpty(), "Debe haber al menos un paciente");

        System.out.println("Total pacientes: " + pacientesDTO.size());
        pacientesDTO.forEach(System.out::println);

        //Validamos sobre el @order(1)
        assertTrue(pacientesDTO.stream()
                .anyMatch(p -> p.getNombre().equals("Agustin")));
    }

    @Test
    @Order(4)
    public void actualizarPaciente() {
        // DADO - Quiero actualizar los datos de un paciente
        int id =1;
        String nombreAct = "Agustin Actualizado en Test";
        String apellidoAct = "Lopez Actualizado en Test2";
        int numeroAct = 1111111;
        String emailAct  = "agustin@testunit.com";
        Optional<Paciente> pacienteBuscado = pacienteService.buscarPacientePorId(id);
        assertTrue(pacienteBuscado.isPresent(), "Debe existir el paciente con "+ id + " para esta prueba");

        Paciente paciente = pacienteBuscado.get();
        System.out.println("Antes: " + paciente);

        // CUANDO - Ingresan los nuevos datos (solo permitidos para actualizar)
        paciente.setNombre(nombreAct);
        paciente.setApellido(apellidoAct);
        paciente.setNumeroContacto(numeroAct);
        paciente.setEmail(emailAct);

        // ENTONCES - Actualizo el paciente
        Paciente pacienteActualizado = pacienteService.actualizarPaciente(paciente);

        assertEquals(nombreAct, pacienteActualizado.getNombre());
        assertEquals(apellidoAct, pacienteActualizado.getApellido());
        assertEquals(numeroAct, pacienteActualizado.getNumeroContacto());
        assertEquals(emailAct, pacienteActualizado.getEmail());

        System.out.println("Paciente Actualizado: " + pacienteActualizado);
    }

    @Test
    @Order(5)
    public void eliminarPaciente() {
        //DADO - Quiero eliminar un paciente
        int id = 1;
        Optional<Paciente> pacienteBuscado = pacienteService.buscarPacientePorId(id);
        assertTrue(pacienteBuscado.isPresent(), "Debe existir el paciente "+id+" para esta prueba");

        Paciente paciente = pacienteBuscado.get();
        System.out.println("Antes: " + paciente);

        //CUANDO - Se nos solicita eliminarlo
        pacienteService.eliminarPaciente(paciente.getId());

        //ENOTNCES - Verificamos
        Optional<Paciente> pacienteEliminado = pacienteService.buscarPacientePorId(id);
        assertTrue(pacienteEliminado.isEmpty(), "El paciente "+ id +"debería haber sido eliminado");

        System.out.println("Paciente con " + id + " eliminado correctamente");
    }
}
