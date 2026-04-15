package org.javinttdata.cliente.service;

import org.javinttdata.cliente.model.Cliente;
import org.javinttdata.cliente.model.ClienteBuilder;
import org.javinttdata.cliente.repository.ClienteRepository;
import org.javinttdata.cliente.repository.ClientesRepositoryJdbc;

import java.util.List;
import java.util.Optional;

public class ClienteService {

    private final ClienteRepository repository; // ✅ INTERFAZ

    public ClienteService(ClienteRepository repository) { // ✅ INTERFAZ
        this.repository = repository;
    }

    public Cliente crearCliente(String nombre, String apellidos, String dni, String email, String telefono) {
        //Hacemos uso de builder de cliente
        Cliente cliente = new ClienteBuilder()
                .nombre(nombre)
                .apellidos(apellidos)
                .dni(dni)
                .email(email)
                .telefono(telefono)
                .build();
        repository.guardar(cliente);
        return cliente;
    }

    public Cliente buscarPorId(long id) {
        return repository.buscarPorId(id).orElse(null);
    }

    public Cliente buscarPorDni(String dni) {
        return repository.buscarPorDni(dni).orElse(null);
    }

    public List<Cliente> obtenerTodos() {
        return repository.listarTodos();
    }

}
