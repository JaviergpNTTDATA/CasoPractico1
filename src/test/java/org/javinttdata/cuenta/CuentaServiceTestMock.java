package org.javinttdata.cuenta;

import org.javinttdata.cliente.model.Cliente;
import org.javinttdata.cliente.repository.ClientesRepositoryJdbc;
import org.javinttdata.cuenta.model.Cuenta;
import org.javinttdata.cuenta.repository.CuentaRepositoryJdbc;
import org.javinttdata.cuenta.service.CuentaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CuentaServiceTestMock {
    @Mock
    private CuentaRepositoryJdbc cuentaRepository;

    @Mock
    private ClientesRepositoryJdbc clientesRepository;

    @InjectMocks
    private CuentaService cuentaService;

    private Cliente cliente;

    @BeforeEach
    void setUp() {
        cliente = new Cliente();
        cliente.setId(1L);
    }

    /**
     * Test que comprueba que devuelve un null si no encuentral acuenta
     */
    @Test
    void noDeberiaCrearCuentaNoExisteCliente() {

        when(clientesRepository.buscarPorId(1L))
                .thenReturn(Optional.empty());

        Cuenta cuenta = cuentaService.crearCuenta(1L);

        assertNull(cuenta);

        verify(cuentaRepository, never())
                .guardar(any());
    }

    /**
     * Test que comprueba si funciona la busqueda por iban
     */
    @Test
    void deberiaDevolverCuentaAlBuscarPorIban() {

        Cuenta cuenta = new Cuenta();
        cuenta.setIban("ES123");

        when(cuentaRepository.buscarPorNumero("ES123"))
                .thenReturn(Optional.of(cuenta));

        Cuenta resultado = cuentaService.buscarPorIban("ES123");

        assertNotNull(resultado);
        assertEquals("ES123", resultado.getIban());
    }
}
