import dao.PacienteDAOH2;
import entity.Domicilio;
import entity.Paciente;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import service.PacienteService;

import java.time.LocalDate;
import java.util.List;

public class PacienteTestService extends BaseH2Test{
    @Test
    public void buscarPaciente(){
        //DADO
        // -- Se comenta el código de crear tablas para que lo haga por una unica vez en BaseH2Test
        //BD.crearTablas();
        PacienteService pacienteService= new PacienteService(new PacienteDAOH2());
        //CUANDO
        Paciente paciente= pacienteService.buscarPacientePorId(1);
        System.out.println("datos encontrados: "+paciente.toString());
        //ENTONCES
        Assertions.assertTrue(paciente!=null);
    }

    @Test
    public void guardarPaciente(){
        //DADO - Tenemos un nuevo paciente pero vive en el mismo Domicilio que esta en la DB
        PacienteService pacienteService = new PacienteService(new PacienteDAOH2());

        // Domicilio existente (ya está en la BD)
        Domicilio domExistente = new Domicilio();
        domExistente.setId(1);

        // Paciente nuevo (usa el domicilio existente)
        LocalDate fecha = LocalDate.now();
        Paciente nuevoPaciente = new Paciente("Agustin", "Prueba", 115050, fecha,domExistente,"agustin@prueba.com");

        //CUANDO - Guardamos el paciente
        Paciente pacienteGuardado = pacienteService.guardarPaciente(nuevoPaciente);

        // ENTONCES - verificamos que se haya guardado correctamente
        Assertions.assertNotNull(pacienteGuardado.getId());
        System.out.println("Paciente guardado correctamente: " + pacienteGuardado.toString());
    }

    @Test
    public void listarPacientes(){
        //DADO - Queremos ver la tabla Pacientes
        PacienteService pacienteService = new PacienteService(new PacienteDAOH2());

        //CUANDO - Queremos saber cuales pacientes tenemos en la clinica
        List<Paciente> pacientes = pacienteService.buscarPacientes();

        // ENTONCES - Verificación
        Assertions.assertNotNull(pacientes, "La lista no debe ser null");
        Assertions.assertFalse(pacientes.isEmpty(), "Debe haber al menos un paciente");

        System.out.println("Total pacientes: " + pacientes.size());
        pacientes.forEach(p -> System.out.println(p));
    }

    @Test
    public void actualizarPaciente(){
        //DADO
        // Queremos actualizar el nombre, apellido, telefono o correo
        // Del Paciente que ya existe
        PacienteService pacienteService = new PacienteService(new PacienteDAOH2());

        Paciente paciente = pacienteService.buscarPacientePorId(1);
        Assertions.assertNotNull(paciente, "Debe existir el paciente id=1 para esta prueba");
        System.out.println("Antes: " + paciente);

        //CUANDO - Un paciente nos pide modificar los sig. datos
        paciente.setNombre("Homero MOD");
        paciente.setApellido("Simpson MOD");
        paciente.setNumeroContacto(22222);
        paciente.setEmail("homer.MOD@disney.com");

        pacienteService.actualizarPaciente(paciente);

        //ENTONCES - VERIFICAMOS DATOS NUEVOS
        Paciente pacienteNuevo = pacienteService.buscarPacientePorId(paciente.getId());
        Assertions.assertEquals("Homero MOD", pacienteNuevo.getNombre());
        Assertions.assertEquals("Simpson MOD", pacienteNuevo.getApellido());
        Assertions.assertEquals(22222, pacienteNuevo.getNumeroContacto());
        Assertions.assertEquals("homer.MOD@disney.com", pacienteNuevo.getEmail());

        System.out.println("Actualizado: " + pacienteNuevo);
    }

    @Test
    public void eliminarPaciente(){
        //DADO
        //queremos eliminar un registro
        //del Paciente que ya existe
        PacienteService pacienteService = new PacienteService(new PacienteDAOH2());

        Paciente paciente = pacienteService.buscarPacientePorId(1);
        Assertions.assertNotNull(paciente, "Debe existir el paciente id=1 para esta prueba");
        System.out.println("Antes: " + paciente);

        //CUANDO - Se nos solicita eliminarlo
        pacienteService.eliminarPaciente(paciente.getId());

        //ENOTNCES - Verificamos
        Paciente pacienteEliminado = pacienteService.buscarPacientePorId(1);
        Assertions.assertNull(pacienteEliminado, "El paciente ID=1 debería haber sido eliminado");

        System.out.println("Paciente con"+paciente.getId()+"eliminado correctamente");
    }
}
