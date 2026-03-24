package org.javinttdata.cliente.repository;

import org.javinttdata.cliente.model.Cliente;

import java.util.*;

public class ClientesRepository {

    public final Map<String, Cliente> clientesPorDni = new HashMap<>();
    private final Set<String> emailsRegistrados = new HashSet<>();
    private final Set<String> telefonosRegistrados = new HashSet<>();
    private int secuenciaId = 1;

    public void guardar(Cliente cliente) {

        if (clientesPorDni.containsKey(cliente.getDni())) {
            throw new IllegalArgumentException(
                    "ERROR : Ya existe un cliente con el DNI " + cliente.getDni()
            );
        }

        if (emailsRegistrados.contains(cliente.getEmail())) {
            throw new IllegalArgumentException(
                    "ERROR : Ya existe un cliente con el EMAIL " + cliente.getEmail().toUpperCase()
            );
        }

        if (telefonosRegistrados.contains(cliente.getTelefono())) {
            throw new IllegalArgumentException(
                    "ERROR : Ya existe un cliente con el TELEFONO " + cliente.getTelefono()
            );
        }

        cliente.setId(secuenciaId++);

        clientesPorDni.put(cliente.getDni(), cliente);
        emailsRegistrados.add(cliente.getEmail());
        telefonosRegistrados.add(cliente.getTelefono());
    }

    public List<Cliente> obtenerTodos() {
        return new ArrayList<>(clientesPorDni.values());
    }

    public Cliente buscarPorDni(String dni) {
        return clientesPorDni.get(dni.toUpperCase());
    }

    public Cliente buscarPorId(int id) {

        for (Cliente cliente : clientesPorDni.values()) {
            if (cliente.getId() == id) {
                return cliente;
            }
        }

        return null;
    }

}
