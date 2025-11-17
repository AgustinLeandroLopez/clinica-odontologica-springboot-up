package com.ClinicaOdontologica.UP.service;

import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false) //Quitamos los filtros de seguridad
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TurnoTestIntegration {
    @Autowired
    private MockMvc mockMvc;

    private static Integer pacienteId;
    private static Integer odontologoId;
    private static Integer turnoId;

    @Test
    @Order(1)
    public void crearPaciente() throws Exception {
        String json = """
                {
                    "nombre": "Agustin",
                    "apellido": "Lopez",
                    "numeroContacto": "11111",
                    "fechaIngreso": "2025-11-01",
                    "domicilio": {
                        "calle": "Calle Falsa",
                        "numero": "123",
                        "localidad": "CABA",
                        "provincia": "Buenos Aires"
                    },
                    "email":"agustin7@prueba.com"
                }
                """;

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/paciente")
                        .contentType(String.valueOf(MediaType.APPLICATION_JSON))
                        .content(json))
                .andExpect(status().isCreated())
                .andReturn();
        String response = result.getResponse().getContentAsString();
        pacienteId = JsonPath.read(response, "$.paciente.id");
        assertNotNull(pacienteId);
    }

    @Test
    @Order(2)
    public void crearOdontologo() throws Exception {
        String json = """
                {
                    "nombre": "Sofia",
                    "apellido": "Julian",
                    "matricula": "1122493"
                }
                """;

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/odontologo")
                        .contentType(String.valueOf(MediaType.APPLICATION_JSON))
                        .content(json))
                .andExpect(status().isCreated())
                .andReturn();
        String response = result.getResponse().getContentAsString();
        odontologoId = JsonPath.read(response, "$.odontologo.id");
        assertNotNull(odontologoId);
    }

    @Test
    @Order(3)
    public void crearTurno() throws Exception {
        String json = """
                {
                    "paciente": { "id": %d },
                    "odontologo": { "id": %d },
                    "fecha": "2025-11-17"
                }
                """.formatted(pacienteId, odontologoId);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/turno")
                        .contentType(String.valueOf(MediaType.APPLICATION_JSON))
                        .content(json))
                .andExpect(status().isCreated())
                .andReturn();

        String response = result.getResponse().getContentAsString();
        turnoId = JsonPath.read(response, "$.turno.id");
        assertNotNull(turnoId);
    }

    @Test
    @Order(4)
    public void buscarTurno() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/turno/id/" + turnoId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(turnoId));
    }

    @Test
    @Order(5)
    public void listarTurnos() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/turno"))
                .andExpect(status().isOk())
                .andExpect((ResultMatcher) jsonPath("$").isArray());
    }

    @Test
    @Order(6)
    public void eliminarTurno() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/turno/" + turnoId))
                .andExpect(status().isOk());
    }
}

