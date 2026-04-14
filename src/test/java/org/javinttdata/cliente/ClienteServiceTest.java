package org.javinttdata.cliente;

import org.javinttdata.cliente.model.Cliente;
import org.javinttdata.cliente.model.ClienteBuilder;
import org.javinttdata.cliente.repository.ClienteRepository;
import org.javinttdata.cliente.repository.ClientesRepositoryJdbc;
import org.javinttdata.cliente.service.ClienteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Clase que se encarga de hacer test
 * He tenido que cambiar algunos test del primer caso practico porque ya no tenian coherencia al haber cambiado toda la estrucutra
 */
@ExtendWith(MockitoExtension.class)
class ClienteServiceTest {


    //Declaramos el repositorio
    private ClientesRepositoryJdbc repository;

    /**
     * Antes de cada test inicializamos el repositorio
     */
    @BeforeEach
    void setUp() {
        repository = new ClientesRepositoryJdbc();
    }

    /**
     * En este test comprobamos que se guarde correctamente el cliente
     */
    @Test
    void deberiaGuardarClienteCorrectamente() {
        Cliente c1 = new Cliente("Juan", "Perez", "12345678A", "juan@test.com", "600000000");

        repository2.guardar(c1);

        assertEquals(5, repository.listarTodos().size());
    }

    /**
     * En este test comprobamos que no pueda añadir dos cliente con el mismo dni, en este caso tiene que lanzar una excepcion para que sea valido el test
     */
    @Test
    void noDebePermitirDniDuplicado() {
        Cliente c1 = new Cliente("Juan", "Perez", "12345678A", "juan@test.com", "600000000");
        Cliente c2 = new Cliente("Ana", "Lopez", "12345678A", "ana@test.com", "611111111");

        repository2.guardar(c1);

        assertThrows(RuntimeException.class, () -> {
            repository.guardar(c2);
        });
    }

    @Mock
    private ClientesRepositoryJdbc repository2;

    @InjectMocks
    private ClienteService service;

    /**
     * Test que comprueba la creacion de un cliente con lo que conlleva
     */
    @Test
    void deberiaCrearGuardarYDevolverCliente() {

        Cliente clienteGuardado = new ClienteBuilder()
                .nombre("Juan")
                .apellidos("Perez")
                .dni("12345678A")
                .email("juan@mail.com")
                .telefono("600123123")
                .build();

        when(repository2.guardar(any(Cliente.class)))
                .thenReturn(clienteGuardado);

        Cliente resultado = service.crearCliente(
                "Juan", "Perez", "12345678A",
                "juan@mail.com", "600123123"
        );

        assertNotNull(resultado);
        assertEquals("Juan", resultado.getNombre());

        verify(repository2, times(1)).guardar(any(Cliente.class));
    }

    /**
     * Test que comprueba la busqueda de cliente por id
     */
    @Test
    void buscaPorIdYDebeDevolverlo() {

        Cliente cliente = new Cliente(
                "Ana", "Lopez", "11111111B",
                "ana@mail.com", "600000000"
        );
        cliente.setId(1L);

        when(repository2.buscarPorId(1L))
                .thenReturn(Optional.of(cliente));

        Cliente resultado = service.buscarPorId(1L);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
    }

    /**
     * Test que comprueba que devuelva null si no existe
     */
    @Test
    void buscaIdNoExistenteDebeDevolverNull() {

        when(repository2.buscarPorId(99L))
                .thenReturn(Optional.empty());

        Cliente resultado = service.buscarPorId(99L);

        assertNull(resultado);
    }
}

