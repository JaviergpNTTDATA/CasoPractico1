package org.javinttdata.operacion;

import org.javinttdata.cuenta.model.Cuenta;
import org.javinttdata.cuenta.repository.CuentaRepository;
import org.javinttdata.operacion.model.enums.TipoOperacion;
import org.javinttdata.operacion.repository.OperacionesRepositoryJdbc;
import org.javinttdata.operacion.service.OperacionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;

import java.math.BigDecimal;
import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(org.mockito.junit.jupiter.MockitoExtension.class)
class OperacionServiceTest {

    @Mock
    private CuentaRepository cuentaRepository;

    @Mock
    private OperacionesRepositoryJdbc operacionesRepository;

    @InjectMocks
    private OperacionService operacionService;

    private Cuenta cuenta;

    @BeforeEach
    void setUp() {
        cuenta = new Cuenta(1L);
        cuenta.setId(1L);
        cuenta.setSaldo(BigDecimal.valueOf(1000));
    }

    /**
     * Test que comprueba el correcto funcionamiento de un deposito
     */
    @Test
    void deberiaAumentarSaldo() {

        when(cuentaRepository.buscarPorIban(any(Connection.class), eq("ES123")))
                .thenReturn(cuenta);

        Cuenta resultado = operacionService.depositar("ES123", BigDecimal.valueOf(200));

        assertNotNull(resultado);
        assertEquals(BigDecimal.valueOf(1200), resultado.getSaldo());

        verify(cuentaRepository).actualizarSaldo(any(Connection.class), eq(cuenta));
        verify(operacionesRepository).guardar(
                any(Connection.class),
                eq(1),
                eq(TipoOperacion.DEPOSITO),
                eq(BigDecimal.valueOf(200))
        );
    }

    /**
     * Test que comprueba que no se pueda depositar en negativo
     */
    @Test
    void noDeberiaDepositarNegativo() {

        Cuenta resultado = operacionService.depositar("ES123", BigDecimal.valueOf(-100));

        assertNull(resultado);
        verifyNoInteractions(cuentaRepository);
    }

    /**
     * Comprueba que funcione el retiro de saldo correctamente
     */
    @Test
    void deberiaHacerBienRetiroDeSaldo() {

        when(cuentaRepository.buscarPorIban(any(Connection.class), eq("ES123")))
                .thenReturn(cuenta);

        Cuenta resultado = operacionService.retirar("ES123", BigDecimal.valueOf(300));

        assertNotNull(resultado);
        assertEquals(BigDecimal.valueOf(700), resultado.getSaldo());

        verify(cuentaRepository).actualizarSaldo(any(Connection.class), eq(cuenta));
        verify(operacionesRepository).guardar(
                any(Connection.class),
                eq(1),
                eq(TipoOperacion.RETIRO),
                eq(BigDecimal.valueOf(300))
        );
    }
}
