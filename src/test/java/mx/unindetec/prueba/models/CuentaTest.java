package mx.unindetec.prueba.models;

import mx.unindetec.prueba.exceptions.SaldoInsuficienteException;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.condition.*;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.*;

//Por default el ciclo de vida es PER_METHOD
//@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CuentaTest {
    Cuenta cuenta;

    @BeforeEach
    void metodoInicial(){
        cuenta = new Cuenta("Pedro",new BigDecimal("100.12345"));
        System.out.println("Iniciando el método.");
    }

    @AfterEach
    void tearDown() {
        System.out.println("finalizando el método.");
    }

    @BeforeAll
    static void beforeAll() {
        System.out.println("Antes de todo.");
    }

    @AfterAll
    static void afterAll() {
        System.out.println("Despues de todo.");
    }

    @Test
    @DisplayName("Verificando nombre de la cuenta.")
    void testNombreCuenta() {
        Cuenta cuenta = new Cuenta("Pedro",new BigDecimal("100.34"));
//        cuenta.setPersona("Pedro");
        String esperado = "Pedro";
        String actual = cuenta.getPersona();
        assertEquals(esperado,actual,"El saldo esperado no coincide con el actual.");
    }

    @Test
    @DisplayName("Verificando saldo de la cuenta.")
    void saldoCuenta(){
        assertAll(
                ()-> assertEquals(100.12345,cuenta.getSaldo().doubleValue(),
                        ()->"El saldo esperado no coincide con el actual."),
                ()-> assertFalse(cuenta.getSaldo().compareTo(BigDecimal.ZERO) < 0),
                ()-> assertTrue(cuenta.getSaldo().compareTo(BigDecimal.ZERO) > 0)
        );
    }

    @Test
    void testReferenciaCuenta() {
        Cuenta cuenta2 = new Cuenta("Pedro",new BigDecimal("100.12345"));
        //fail(); //permite hacer que falle una prueba.
        assertEquals(cuenta, cuenta2);
    }

    @Test
    //@Disabled
    void testException(){
        Exception exp = assertThrows(SaldoInsuficienteException.class, ()->{
           cuenta.debito(new BigDecimal(1500));
        });
        String actual = exp.getMessage();
        String esperado = "Saldo insuficiente";
        assertEquals(actual, esperado);
    }
    //cuenta.getSaldo().toPlainString()
    //BigDecimal es inmutable, por lo que operaciones como substract o add, retornan una instancia nueva.

    @Test
    @EnabledOnOs(OS.WINDOWS)
    void pruebaSoloWindows() {

    }

    @Test
    @EnabledOnOs({OS.MAC, OS.LINUX})
    void pruebaSoloMacLinux() {

    }

    @Test
    @DisabledOnOs(OS.MAC)
    void pruebaNoMac() {

    }

    @Test
    @EnabledOnJre(JRE.JAVA_11)
    void soloJava11() {

    }

    @Test
    @EnabledIfSystemProperty(named = "java.version",matches = ".*11.*", disabledReason = "Solo para java 11")
    void imprimirJavaVersion() {

    }

    @Test
    @EnabledIfSystemProperty(named = "os.arch",matches = ".*64.*", disabledReason = "Solo para arquitectua 64 bits")
    void testSolo64Bits() {

    }

    @Test
    void imprimirSystemProperties(){
        Properties properties = System.getProperties();
        properties.forEach((k,v)-> System.out.println(k + " : " + v));
    }

    @Test
    @EnabledIfSystemProperty(named = "ENV", matches = "dev", disabledReason = "solo para ambientes de desarrollo")
    void testDev() {

    }

    @Test
    void imprimirVariablesEntorno(){
        Map<String, String> getenv = System.getenv();
        getenv.forEach((k,v)-> System.out.println(k + " : " + v));
    }

    @Test
    @EnabledIfEnvironmentVariable(named = "JAVA_HOME", matches = ".*11.0.6.*",disabledReason = "Solo para java 11")
    void testJavaHome(){

    }

    @Test
    @EnabledIfEnvironmentVariable(named = "ENVIROMENT", matches = "dev",disabledReason = "Solo para devs")
    void testEnviroment(){

    }

    @Test
    @DisplayName("Test Saldo cuenta")
    void testSaldoCuenta(){
        boolean esDev = "dev".equals(System.getProperty("ENV"));
        assumingThat(esDev,() -> {
            assertNotNull(cuenta.getSaldo());
            assertEquals(100.12345, cuenta.getSaldo().doubleValue());
        });
        assertFalse(cuenta.getSaldo().compareTo(BigDecimal.ZERO) < 0);
        assertTrue(cuenta.getSaldo().compareTo(BigDecimal.ZERO) > 0);
    }
}