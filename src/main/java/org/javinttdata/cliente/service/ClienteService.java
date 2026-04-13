package org.javinttdata.cliente.service;

import org.javinttdata.cliente.model.Cliente;
import org.javinttdata.cliente.repository.ClientesRepository;

import java.util.List;

public class ClienteService {

    private final ClientesRepository repository;

    public ClienteService(ClientesRepository repository) {
        this.repository = repository;
    }

    public Cliente crearCliente(String nombre, String apellidos,
                                String dni, String email, String telefono) {

        Cliente cliente = new Cliente(nombre, apellidos, dni, email, telefono);
        repository.guardar(cliente);
        return cliente;
    }

    public Cliente buscarPorId(long id) {
        return repository.buscarPorId(id);
    }

    public Cliente buscarPorDni(String dni) {
        return repository.buscarPorDni(dni);
    }

    public List<Cliente> obtenerTodos() {
        return repository.obtenerTodos();
    }
}
