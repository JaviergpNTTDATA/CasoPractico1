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

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CuentaServiceTest {
    /**
     * Test que comprueba que genera correctamente el iban de una cuenta
     */
    @Test
    void deberiaGenerarIbanCorrectoYSaldoDebeSer0() {
        Cliente titular = new Cliente("Carlos", "Ruiz", "11223344C", "carlos@test.com", "622222222");
        Cuenta cuenta = new Cuenta(titular.getId());

        //El iban debe comenzar por esta cadena, ya que viene definida asi
        assertTrue(cuenta.getIban().startsWith("ES91210000"));
        //Aqui comprobamos que el saldo al crear la cuenta tiene que ser 0
        assertEquals(0, cuenta.getSaldo());
    }
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
     * Test que comprueba si se crea cuenta cuando exite cliente
     */
    @Test
    void deberiaCrearCuentaSiExisteCliente() {

        when(clientesRepository.buscarPorId(1L))
                .thenReturn(Optional.of(cliente));

        Cuenta cuenta = cuentaService.crearCuenta(1L);

        assertNotNull(cuenta);
        assertEquals(1L, cuenta.getCliente_id());

        verify(cuentaRepository, times(1))
                .guardar(any(Cuenta.class));
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