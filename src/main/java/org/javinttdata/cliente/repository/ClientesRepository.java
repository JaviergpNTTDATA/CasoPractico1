package org.javinttdata.cliente.repository;

import org.javinttdata.cliente.model.Cliente;
import org.javinttdata.config.DatabaseConnectionManager;

import java.sql.*;
import java.util.*;

/**
 * Repositorio de Clientes
 */
public class ClientesRepository {

    /**
     * Metodo que comprueba duplicidad de dni, email y telefono, si no guarda en el Map de arriba
     *
     * @param cliente cliente que se intenta guardad
     */
    public void guardar(Cliente cliente) {

        String sentencia = """
                INSERT INTO clientes (nombre, apellidos, dni, email, telefono)
                VALUES (?, ?, ?, ?, ?)
                """;

        try (Connection conn = DatabaseConnectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sentencia, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, cliente.getNombre());
            stmt.setString(2, cliente.getApellidos());
            stmt.setString(3, cliente.getDni().toUpperCase());
            stmt.setString(4, cliente.getEmail());
            stmt.setString(5, cliente.getTelefono());

            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                cliente.setId(rs.getLong(1));
            }

        } catch (SQLException e) {

            if (e.getMessage().contains("clientes_dni_key")) {
                throw new RuntimeException("ERROR: Ya existe un cliente con ese DNI");
            }

            if (e.getMessage().contains("clientes_email_key")) {
                throw new RuntimeException("ERROR: Ya existe un cliente con ese EMAIL");
            }

            if (e.getMessage().contains("clientes_telefono_key")) {
                throw new RuntimeException("ERROR: Ya existe un cliente con ese TELÉFONO");
            }

            throw new RuntimeException("Error al guardar cliente: " + e.getMessage());
        }
    }

    /**
     * Metodo para obtener todos los clientes
     *
     * @return devuelve una lista de todos los clientes
     */
    public List<Cliente> obtenerTodos() {
        List<Cliente> lista = new ArrayList<>();
        String sentencia = """
                SELECT * FROM clientes
                """;

        try (Connection conn = DatabaseConnectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sentencia)) {

            ResultSet rs = stmt.executeQuery();

            while(rs.next())
            {
                Cliente encontrado = new Cliente(
                        rs.getString("nombre"),
                        rs.getString("apellidos"),
                        rs.getString("dni"),
                        rs.getString("email"),
                        rs.getString("telefono")

                );
                encontrado.setId(rs.getLong("id"));
                lista.add(encontrado);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return lista;
    }

    /**
     * Metodo para buscar cliente por dni
     *
     * @param dni el dni del cliente que estamos buscando
     * @return devuelve el cliente con el mismo dni
     */
    public Cliente buscarPorDni(String dni) {
        String sentencia = """
                SELECT * FROM clientes WHERE dni = ?
                """;

        try (Connection conn = DatabaseConnectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sentencia)) {

            stmt.setString(1, dni.toUpperCase());

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Cliente encontrado = new Cliente(
                        rs.getString("nombre"),
                        rs.getString("apellidos"),
                        rs.getString("dni"),
                        rs.getString("email"),
                        rs.getString("telefono")

                );
                encontrado.setId(rs.getLong("id"));
                return encontrado;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    /**
     * Metodo para buscar cliente por id
     *
     * @param id el id del cliente que estamos buscando
     * @return devuelve el cliente con el mismo id
     */
    public Cliente buscarPorId(long id) {

        String sentencia = """
                SELECT * FROM clientes WHERE id = ?
                """;

        try (Connection conn = DatabaseConnectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sentencia)) {

            stmt.setLong(1, id);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Cliente encontrado = new Cliente(
                        rs.getString("nombre"),
                        rs.getString("apellidos"),
                        rs.getString("dni"),
                        rs.getString("email"),
                        rs.getString("telefono")

                );
                encontrado.setId(rs.getLong("id"));
                return encontrado;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

}
