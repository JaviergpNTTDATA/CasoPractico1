package org.javinttdata.consulta;

import org.javinttdata.consulta.service.ConsultaService;
import org.javinttdata.cuenta.model.Cuenta;
import org.javinttdata.cuenta.repository.CuentaRepository;
import org.javinttdata.operacion.model.Operacion;
import org.javinttdata.operacion.repository.OperacionesRepositoryJdbc;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ConsultaServiceTestMock {

    @Mock
    private CuentaRepository cuentaRepository;

    @Mock
    private OperacionesRepositoryJdbc operacionesRepository;

    @InjectMocks
    private ConsultaService service;

    @Test
    void deberiaDevolverSaldoSiExsite() {

        Cuenta cuenta = new Cuenta();
        cuenta.setId(1L);
        cuenta.setIban("ES123");

        when(cuentaRepository.buscarPorNumero("ES123"))
                .thenReturn(Optional.of(cuenta));

        Cuenta resultado = service.consultarSaldo("ES123");

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        verify(cuentaRepository).buscarPorNumero("ES123");
    }

    @Test
    void deveriaDevolverListaMovimientos() {

        Cuenta cuenta = new Cuenta();
        cuenta.setId(1L);
        cuenta.setIban("ES123");

        List<Operacion> lista = List.of(new Operacion(), new Operacion());

        when(cuentaRepository.buscarPorNumero("ES123"))
                .thenReturn(Optional.of(cuenta));

        when(operacionesRepository.buscarPorCuentaId(1L))
                .thenReturn(lista);

        List<Operacion> resultado = service.historialMovimientos("ES123");

        assertEquals(2, resultado.size());
        verify(operacionesRepository).buscarPorCuentaId(1L);
    }

    @Test
    void deveriaDevolverMovimientosFecha() {

        Cuenta cuenta = new Cuenta();
        cuenta.setId(1L);
        cuenta.setIban("ES123");

        List<Operacion> lista = List.of(new Operacion());

        LocalDate inicio = LocalDate.of(2024, 1, 1);
        LocalDate fin = LocalDate.of(2024, 1, 31);

        when(cuentaRepository.buscarPorNumero("ES123"))
                .thenReturn(Optional.of(cuenta));

        when(operacionesRepository.buscarPorCuentaIdYFechas(
                eq(1L),
                any(LocalDateTime.class),
                any(LocalDateTime.class)))
                .thenReturn(lista);

        List<Operacion> resultado =
                service.movimientosPorFecha("ES123", inicio, fin);

        assertEquals(1, resultado.size());

        verify(operacionesRepository)
                .buscarPorCuentaIdYFechas(
                        eq(1L),
                        any(LocalDateTime.class),
                        any(LocalDateTime.class));
    }
}
