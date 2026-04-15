package org.javinttdata.cliente;

import org.javinttdata.cliente.model.Cliente;
import org.javinttdata.cliente.model.ClienteBuilder;
import org.javinttdata.cliente.repository.ClienteRepository;
import org.javinttdata.cliente.service.ClienteService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClienteServiceTestMock {

    @Mock
    private ClienteRepository repository;

    @InjectMocks
    private ClienteService service;

    @Test
    void deberiaCrearGuardarYDevolverCliente() {

        Cliente clienteGuardado = new ClienteBuilder()
                .nombre("Juan")
                .apellidos("Perez")
                .dni("12345678A")
                .email("juan@mail.com")
                .telefono("600123123")
                .build();

        when(repository.guardar(any(Cliente.class)))
                .thenReturn(clienteGuardado);

        Cliente resultado = service.crearCliente(
                "Juan", "Perez", "12345678A",
                "juan@mail.com", "600123123"
        );

        assertNotNull(resultado);
        verify(repository, times(1)).guardar(any(Cliente.class));
    }
}
