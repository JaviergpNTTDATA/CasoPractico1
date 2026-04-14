package org.javinttdata.cliente.repository;

import org.javinttdata.cliente.model.Cliente;
import org.javinttdata.config.DatabaseConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ClientesRepositoryJdbc implements ClienteRepository {

    /**
     * Meotodo que guarda cliente
     * @param cliente
     * @return cliente creado
     */
    @Override
    public Cliente guardar(Cliente cliente) {

        String sql = """
                INSERT INTO clientes (nombre, apellidos, dni, email, telefono)
                VALUES (?, ?, ?, ?, ?)
                """;

        try (Connection conn = DatabaseConnectionManager.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

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

            return cliente;

        } catch (SQLException e) {
            throw new RuntimeException("Error al guardar cliente", e);
        }
    }

    /**
     * Metodo que busca por id al cliente
     * @param id
     * @return cliente encontrado
     */
    @Override
    public Optional<Cliente> buscarPorId(Long id) {

        String sql = "SELECT * FROM clientes WHERE id = ?";

        try (Connection conn = DatabaseConnectionManager.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Cliente cliente = mapRowToCliente(rs);
                cliente.setId(rs.getLong("id"));

                return Optional.of(cliente);
            }

            return Optional.empty();

        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar cliente por ID", e);
        }
    }

    /**
     * Metodo que busca por dni al cliente
     * @param dni
     * @return cliente encontrado
     */
    @Override
    public Optional<Cliente> buscarPorDni(String dni) {

        String sql = "SELECT * FROM clientes WHERE dni = ?";

        try (Connection conn = DatabaseConnectionManager.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, dni.toUpperCase());

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Cliente cliente = mapRowToCliente(rs);

                cliente.setId(rs.getLong("id"));

                return Optional.of(cliente);
            }

            return Optional.empty();

        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar cliente por DNI", e);
        }
    }

    /**
     * Metodo que devuelve una lista de todos los clientes
     * @return lista de clientes
     */
    @Override
    public List<Cliente> listarTodos() {

        List<Cliente> lista = new ArrayList<>();

        String sql = "SELECT * FROM clientes";

        try (Connection conn = DatabaseConnectionManager.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Cliente cliente = mapRowToCliente(rs);

                cliente.setId(rs.getLong("id"));
                lista.add(cliente);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al listar clientes", e);
        }

        return lista;
    }

    //Este no lo he implementado en ningun lado porque no se ha pedido que se implemente solo que queria cumplir con la interfaz
    @Override
    public void eliminar(Long id) {

        String sql = "DELETE FROM clientes WHERE id = ?";

        try (Connection conn = DatabaseConnectionManager.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar cliente", e);
        }
    }

    /**
     * Metodo que sirve para no tener que estar repitiendo la misma sentencia tantas veces
     * @param rs
     * @return devuelve un cliente
     * @throws SQLException
     */
    private Cliente mapRowToCliente(ResultSet rs) throws SQLException {
        Cliente cliente = new Cliente(
                rs.getString("nombre"),
                rs.getString("apellidos"),
                rs.getString("dni"),
                rs.getString("email"),
                rs.getString("telefono")
        );
        cliente.setId(rs.getLong("id"));
        return cliente;
    }

}
