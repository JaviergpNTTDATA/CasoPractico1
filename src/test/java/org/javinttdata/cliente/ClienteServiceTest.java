package org.javinttdata.cliente;

import org.javinttdata.cliente.model.Cliente;
import org.javinttdata.cliente.repository.ClientesRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Clase que se encarga de hacer test
 */
class ClienteServiceTest {

    //Declaramos el repositorio
    private ClientesRepository repository;

    /**
     * Antes de cada test inicializamos el repositorio
     */
    @BeforeEach
    void setUp() {
        repository = new ClientesRepository();
    }

    /**
     * En este test comprobamos que se guarde correctamente el cliente
     */
    @Test
    void deberiaGuardarClienteCorrectamente() {
        Cliente cliente = new Cliente("Juan", "Perez", "12345678A", "juan@test.com", "600000000");

        repository.guardar(cliente);

        assertEquals(1, repository.obtenerTodos().size());
    }

    /**
     * En este test comprobamos que no pueda añadir dos cliente con el mismo dni, en este caso tiene que lanzar una excepcion para que sea valido el test
     */
    @Test
    void noDebePermitirDniDuplicado() {
        Cliente c1 = new Cliente("Juan", "Perez", "12345678A", "juan@test.com", "600000000");
        Cliente c2 = new Cliente("Ana", "Lopez", "12345678A", "ana@test.com", "611111111");

        repository.guardar(c1);

        assertThrows(IllegalArgumentException.class, () -> {
            repository.guardar(c2);
        });
    }

    /**
     * Test que comprueba que funcione correctamente la busqueda de cliente por dni
     */
    @Test
    void deberiaBuscarPorDni() {
        Cliente cliente = new Cliente("Juan", "Perez", "12345678A", "juan@test.com", "600000000");

        repository.guardar(cliente);

        Cliente encontrado = repository.buscarPorDni("12345678A");

        assertNotNull(encontrado);
        assertEquals("Juan", encontrado.getNombre());
    }
}
