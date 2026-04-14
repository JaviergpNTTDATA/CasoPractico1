package org.javinttdata.operacion;

import org.javinttdata.cuenta.model.Cuenta;
import org.javinttdata.cuenta.repository.CuentaRepository;
import org.javinttdata.operacion.repository.OperacionesRepositoryJdbc;
import org.javinttdata.operacion.service.OperacionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
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
        cuenta.setIban("ES123");
    }

    @Test
    void deberiaAumentarSaldo() {

        when(cuentaRepository.buscarPorNumero(eq("ES123"), any(Connection.class)))
                .thenReturn(Optional.of(cuenta));

        Cuenta resultado =
                operacionService.depositar("ES123", BigDecimal.valueOf(200));

        assertNotNull(resultado);
        assertEquals(BigDecimal.valueOf(1200), resultado.getSaldo());

        verify(cuentaRepository).actualizarSaldo(
                eq(1L),
                eq(BigDecimal.valueOf(1200)),
                any(Connection.class)
        );

        verify(operacionesRepository).guardar(
                any(Connection.class),
                eq(1),
                any()
        );
    }

    @Test
    void noDeberiaDepositarNegativo() {

        Cuenta resultado =
                operacionService.depositar("ES123", BigDecimal.valueOf(-100));

        assertNull(resultado);
        verifyNoInteractions(cuentaRepository);
    }

    @Test
    void deberiaHacerBienRetiroDeSaldo() {

        when(cuentaRepository.buscarPorNumero(eq("ES123"), any(Connection.class)))
                .thenReturn(Optional.of(cuenta));

        Cuenta resultado =
                operacionService.retirar("ES123", BigDecimal.valueOf(300));

        assertNotNull(resultado);
        assertEquals(BigDecimal.valueOf(700), resultado.getSaldo());

        verify(cuentaRepository).actualizarSaldo(
                eq(1L),
                eq(BigDecimal.valueOf(700)),
                any(Connection.class)
        );

        verify(operacionesRepository).guardar(
                any(Connection.class),
                eq(1),
                any()
        );
    }
}
