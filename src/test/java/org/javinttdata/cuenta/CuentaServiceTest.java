package org.javinttdata.cuenta;

import org.javinttdata.cliente.model.Cliente;
import org.javinttdata.cuenta.model.Cuenta;
import org.javinttdata.cuenta.repository.CuentaRepository;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Cuenta que se encarga de los test de la clase cuenta
 */
class CuentaServiceTest {
    /**
     * Test que comprueba que genera correctamente el iban de una cuenta
     */
    @Test
    void deberiaGenerarIbanCorrectoYSaldoDebeSer0() {
        Cliente titular = new Cliente("Carlos", "Ruiz", "11223344C", "carlos@test.com", "622222222");
        Cuenta cuenta = new Cuenta(titular);

        //El iban debe comenzar por esta cadena, ya que viene definida asi
        assertTrue(cuenta.getIban().startsWith("ES91210000"));
        //Aqui comprobamos que el saldo al crear la cuenta tiene que ser 0
        assertEquals(0, cuenta.getSaldo());
    }
}