package org.javinttdata.cliente.repository;

import org.javinttdata.cliente.model.Cliente;

import java.util.*;

/**
 * Repositorio de Clientes
 */
public class ClientesRepository {

    public final Map<String, Cliente> clientesPorDni = new HashMap<>();//Guardamos los clientes por dni
    private final Set<String> emailsRegistrados = new HashSet<>();//Set de email registrados
    private final Set<String> telefonosRegistrados = new HashSet<>();//Set de telefonos registrados
    private long secuenciaId = 1000L;

    /**
     * Metodo que comprueba duplicidad de dni, email y telefono, si no guarda en el Map de arriba
     * @param cliente cliente que se intenta guardad
     */
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

        clientesPorDni.put(cliente.getDni().toUpperCase(), cliente);
        emailsRegistrados.add(cliente.getEmail());
        telefonosRegistrados.add(cliente.getTelefono());
    }

    /**
     * Metodo para obtener todos los clientes
     * @return devuelve una lista de todos los clientes
     */
    public List<Cliente> obtenerTodos() {
        return new ArrayList<>(clientesPorDni.values());
    }

    /**
     * Metodo para buscar cliente por dni
     * @param dni el dni del cliente que estamos buscando
     * @return devuelve el cliente con el mismo dni
     */
    public Cliente buscarPorDni(String dni) {
        return clientesPorDni.get(dni.toUpperCase());
    }

    /**
     * Metodo para buscar cliente por id
     * @param id el id del cliente que estamos buscando
     * @return devuelve el cliente con el mismo id
     */
    public Cliente buscarPorId(long id) {

        for (Cliente cliente : clientesPorDni.values()) {
            if (cliente.getId() == id) {
                return cliente;
            }
        }
        return null;
    }

}
