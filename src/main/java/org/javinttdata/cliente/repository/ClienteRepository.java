package org.javinttdata.cliente.repository;

import org.javinttdata.cliente.model.Cliente;

import java.util.List;
import java.util.Optional;

//Interfaz que cumplimos el cl repositorio de cliente
public interface ClienteRepository {
    Cliente guardar(Cliente cliente);
    Optional<Cliente> buscarPorId(Long id);
    Optional<Cliente> buscarPorDni(String dni);
    List<Cliente> listarTodos();
    void eliminar(Long id);
} 