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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(org.mockito.junit.jupiter.MockitoExtension.class)
class OperacionServiceTestMock {

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

        when(cuentaRepository.buscarPorNumero(eq("ES123"), any()))
                .thenReturn(Optional.of(cuenta));

        Cuenta resultado =
                operacionService.depositar("ES123", BigDecimal.valueOf(200));

        assertEquals(BigDecimal.valueOf(1200), resultado.getSaldo());

        verify(cuentaRepository).actualizarSaldo(
                eq(1L),
                eq(BigDecimal.valueOf(1200)),
                any()
        );
    }
}
