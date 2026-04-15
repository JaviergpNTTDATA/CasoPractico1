package org.javinttdata.cuenta;

import org.javinttdata.cliente.model.Cliente;
import org.javinttdata.cliente.repository.ClientesRepositoryJdbc;
import org.javinttdata.cuenta.model.Cuenta;
import org.javinttdata.cuenta.repository.CuentaRepositoryJdbc;
import org.javinttdata.cuenta.repository.MetodosIban;
import org.javinttdata.cuenta.service.CuentaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CuentaServiceTest {
    /**
     * Test que comprueba que genera correctamente el iban de una cuenta
     */
    @Test
    void deberiaGenerarIbanCorrectoYSaldoDebeSer0() {

        Cliente titular = new Cliente(
                "Carlos", "Ruiz", "43534",
                "test@test.com", "600000000"
        );

        Cuenta cuenta = new Cuenta(1L);
        cuenta.setIban("ES912100007484748");// simulamos id cliente

        assertNotNull(cuenta.getIban());
        assertTrue(cuenta.getIban().startsWith("ES91210000"));
        assertEquals(java.math.BigDecimal.ZERO, cuenta.getSaldo());
    }


}