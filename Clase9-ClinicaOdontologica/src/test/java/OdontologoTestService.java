import dao.OdontologoDAOH2;
import model.Odontologo;
import service.OdontologoService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.util.List;


public class OdontologoTestService extends BaseH2Test{
    @Test
    public void buscarOdontologo(){
        //DADO
        OdontologoService odontologoService= new OdontologoService(new OdontologoDAOH2());
        //CUANDO
        Odontologo odontologo= odontologoService.buscarOdontologoPorId(1);
        System.out.println("Datos encontrados: "+odontologo.toString());
        //ENTONCES
        Assertions.assertTrue(odontologo!=null);
    }

    @Test
    public void guardarOdontologo(){
        //DADO - Queremos crear un Odontologo
        OdontologoService odontologoService = new OdontologoService(new OdontologoDAOH2());
        Odontologo nuevoOdontologo = new Odontologo("Jose", "Maria", 2005);

        //CUANDO - Tenemos un nuevo empleado
        Odontologo odontologoGuardado = odontologoService.guardarOdontologo(nuevoOdontologo);

        // ENTONCES - Verificamos que se haya guardado correctamente
        Assertions.assertNotNull(odontologoGuardado.getId());
        System.out.println("Odontologo guardado correctamente: " + odontologoGuardado.toString());
    }

    @Test
    public void listarOdontologos(){
        //DADO - Queremos ver la tabla Odontologos
        OdontologoService odontologoService = new OdontologoService(new OdontologoDAOH2());

        //CUANDO - Queremos saber cuales odontologos tenemos en la clinica
        List<Odontologo> odontologos = odontologoService.buscarOdontologo();

        // ENTONCES - Verificación
        Assertions.assertNotNull(odontologos, "La lista no debe ser null");
        Assertions.assertFalse(odontologos.isEmpty(), "Debe haber al menos un paciente");

        System.out.println("Total odontologos: " + odontologos.size());
        odontologos.forEach(p -> System.out.println(p));
    }

    @Test
    public void eliminarOdontologo(){
        //DADO
        //queremos eliminar un registro
        //del Odontologo que ya existe
        OdontologoService odontologoService = new OdontologoService(new OdontologoDAOH2());
        int id = 2;

        Odontologo odontologo = odontologoService.buscarOdontologoPorId(id);
        Assertions.assertNotNull(odontologo, "Debe existir el odontologo numero"+id+"para esta prueba");
        System.out.println("Antes: " + odontologo.toString());

        //CUANDO - Se nos solicita eliminarlo
        odontologoService.eliminarOdontologo(odontologo.getId());

        //ENOTNCES - Verificamos
        Odontologo odontologoEliminado = odontologoService.buscarOdontologoPorId(id);
        Assertions.assertNull(odontologoEliminado, "El odontologo"+id+"debería haber sido eliminado");

        System.out.println("Odontologo con ID"+odontologo.getId()+"eliminado correctamente");
    }
}
