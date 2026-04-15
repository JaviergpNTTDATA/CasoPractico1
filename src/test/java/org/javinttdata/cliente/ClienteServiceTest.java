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
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Clase que se encarga de hacer test
 * He tenido que cambiar algunos test del primer caso practico porque ya no tenian coherencia al haber cambiado toda la estrucutra
 */
class ClienteServiceTest {

    private ClientesRepositoryJdbc repository;

    @BeforeEach
    void setUp() {
        repository = new ClientesRepositoryJdbc();
    }

    /**
     * Comrprueba que se crea y se guarda correctamente el cliente
     */
    @Test
    void deberiaGuardarClienteCorrectamente() {

        String dni = UUID.randomUUID().toString().substring(0,8);

        Cliente c1 = new Cliente("Juan", "Perez",
                dni, "ygygrgegy@test.com", "3453453");

        repository.guardar(c1);

        List<Cliente> lista = repository.listarTodos();

        assertTrue(lista.size() > 0);
    }

    /**
     * Comprueba duplicidad de datos
     */
    @Test
    void noDebePermitirDniDuplicado() {

        String dni = "DNI" + System.currentTimeMillis();

        Cliente c1 = new Cliente("Juan", "Perez",
                dni, "juan@test.com", "600000000");

        Cliente c2 = new Cliente("Ana", "Lopez",
                dni, "ana@test.com", "611111111");

        assertThrows(RuntimeException.class, () -> {
            repository.guardar(c1);
        });
        assertThrows(RuntimeException.class, () -> {
            repository.guardar(c2);
        });
    }

}
