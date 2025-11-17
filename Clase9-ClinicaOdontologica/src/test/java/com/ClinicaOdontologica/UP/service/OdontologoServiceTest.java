package com.ClinicaOdontologica.UP.service;

import com.ClinicaOdontologica.UP.entity.Odontologo;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class OdontologoServiceTest {
    @Autowired
    OdontologoService odontologoService;

    @Test
    @Order(1)
    public void guardarOdontologo() {
        // DADO - Quiero guardar un odontologo
        Odontologo odontologo = new Odontologo(
                "Jose",
                "Maria",
                000012
        );
        // CUANDO - Guardo ese odontologo
        Odontologo odontologoGuardado = odontologoService.guardarOdontologo(odontologo);
        System.out.println("Odontologo guardado: " + odontologoGuardado);
        // ENTONCES - Debe asignarse un ID
        assertNotNull(odontologoGuardado.getId());
    }

    @Test
    @Order(2)
    public void buscarOdontologoPorId() {
        // DADO - Tengo un ID para buscar
        int id = 1;
        // CUANDO - Tengo el ID odontologo para buscar
        Optional<Odontologo> odontologoBuscado= odontologoService.buscarOdontologoPorId(id);
        System.out.println("datos encontrados: " + odontologoBuscado);
        // ENTONCES - Debe existir
        assertEquals(id, odontologoBuscado.get().getId());
    }

    @Test
    @Order(3)
    public void listarOdontologos() {
        // DADO -
        // CUANDO -
        List<Odontologo> odontologo = odontologoService.buscarOdontologos();
        // ENTONCES -
        assertNotNull(odontologo, "La lista no debe ser null");
        assertFalse(odontologo.isEmpty(), "Debe haber al menos un odontologo");

        System.out.println("Total odontologos: " + odontologo.size());
        odontologo.forEach(System.out::println);

        //Validamos sobre el @order(1)
        assertTrue(odontologo.stream()
                .anyMatch(p -> p.getNombre().equals("Jose")));
    }

    @Test
    @Order(4)
    public void actualizarOdontologo() {
        // DADO - Quiero actualizar los datos de un odontologo
        int id = 1;
        String nombreAct = "Jose Prueba Actualizado";
        String  apellidoAct = "Maria Prueba Actualizado en test";
        Optional<Odontologo> odontologoBuscado = odontologoService.buscarOdontologoPorId(id);
        assertTrue(odontologoBuscado.isPresent(), "Debe existir el odontologo con "+ id +" para esta prueba");

        Odontologo odontologo = odontologoBuscado.get();
        System.out.println("Antes: " + odontologo);

        // CUANDO - Ingresan los nuevos datos (solo permitidos para actualizar)
        odontologo.setNombre(nombreAct);
        odontologo.setApellido(apellidoAct);

        // ENTONCES - Actualizo el odontologo
        Odontologo odontologoActualizado = odontologoService.actualizarOdontologo(odontologo);

        assertEquals(nombreAct, odontologoActualizado.getNombre());
        assertEquals(apellidoAct, odontologoActualizado.getApellido());

        System.out.println("Odontologo Actualizado: " + odontologoActualizado);
    }

    @Test
    @Order(5)
    public void eliminarOdontologo() {
        //DADO - Quiero eliminar un odontologo
        int id = 1;
        Optional<Odontologo> odontologoBuscado = odontologoService.buscarOdontologoPorId(id);
        assertTrue(odontologoBuscado.isPresent(), "Debe existir el odontologo " +id+" para esta prueba");

        Odontologo odontologo = odontologoBuscado.get();
        System.out.println("Antes: " + odontologo);

        //CUANDO - Se nos solicita eliminarlo
        odontologoService.eliminarOdontologo(odontologo.getId());

        //ENOTNCES - Verificamos
        Optional<Odontologo> odontologoEliminado = odontologoService.buscarOdontologoPorId(id);
        assertTrue(odontologoEliminado.isEmpty(), "El odontologo "+ id +"debería haber sido eliminado");

        System.out.println("Odontologo con " + id + " eliminado correctamente");
    }
}
