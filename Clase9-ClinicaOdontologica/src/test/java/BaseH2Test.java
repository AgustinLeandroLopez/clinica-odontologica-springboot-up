import dao.BD;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS) // permite que el método BeforeAll no sea estático
public abstract class BaseH2Test {
    @BeforeAll
    public void crearPruebasTest(){
        //DADO  - EMPEZAR PRUEBAS
        BD.crearTablas();
        //CUANDO - QUEREMOS PROBAR EN TEST
        System.out.println("Base H2 creada para pruebas en TEST");
        //ENTONCES -

    }
}
