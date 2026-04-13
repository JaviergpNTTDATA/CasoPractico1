package org.javinttdata.cuenta.repository;

import org.javinttdata.cliente.model.Cliente;
import org.javinttdata.config.DatabaseConnectionManager;
import org.javinttdata.cuenta.model.Cuenta;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static org.javinttdata.cuenta.repository.MetodosIban.generarIBAN;

/**
 * Repositorio de Cuenta
 */
public class CuentaRepository {
    /**
     * Metodo que devuelve todas las cuentas
     *
     * @return lista de todas las cuentas
     */
    public List<Cuenta> obtenerTodas() {
        List<Cuenta> lista = new ArrayList<>();
        String sentencia1 = """
                SELECT * FROM cuentas
                """;

        try (Connection conn = DatabaseConnectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sentencia1)) {

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Cliente c;
                String sentencia2 = """
                        SELECT * FROM clientes WHERE id = ?
                        """;
                try (Connection conn2 = DatabaseConnectionManager.getConnection();
                     PreparedStatement stmt2 = conn2.prepareStatement(sentencia2)) {

                    stmt2.setLong(1, rs.getLong("cliente_id"));

                    ResultSet rs2 = stmt2.executeQuery();

                    c = new Cliente(
                            rs2.getString("nombre"),
                            rs2.getString("apellidos"),
                            rs2.getString("dni"),
                            rs2.getString("email"),
                            rs2.getString("telefono")

                    );
                }


                Cuenta encontrada = new Cuenta(c.getId());
                encontrada.setId(rs.getLong("id"));
                lista.add(encontrada);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return lista;
    }

    /**
     * Metodo que devuelve una lista de cuentas en base al id
     *
     * @param idCliente id del titular de las cuentas a buscar
     * @return lista de cuentas con el id
     */
    public List<Cuenta> buscarPorIdCliente(Long idCliente) {
        List<Cuenta> lista = new ArrayList<>();
        String sentencia1 = """
                SELECT * FROM cuentas WHERE cliente_id = ?
                """;

        try (Connection conn = DatabaseConnectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sentencia1)) {

            stmt.setInt(1, idCliente.intValue());

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Cliente c = null;
                String sentencia2 = """
                        SELECT * FROM clientes WHERE id = ?
                        """;
                try (Connection conn2 = DatabaseConnectionManager.getConnection();
                     PreparedStatement stmt2 = conn2.prepareStatement(sentencia2)) {

                    stmt2.setInt(1, idCliente.intValue());

                    ResultSet rs2 = stmt2.executeQuery();

                    if(rs2.next()) {
                        c = new Cliente(
                                rs2.getString("nombre"),
                                rs2.getString("apellidos"),
                                rs2.getString("dni"),
                                rs2.getString("email"),
                                rs2.getString("telefono")

                        );
                        c.setId(rs2.getLong("id"));
                    }
                }
                Cuenta encontrada = new Cuenta();
                encontrada.setIban(rs.getString("numero_cuenta"));
                encontrada.setId(rs.getLong("id"));
                lista.add(encontrada);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return lista;
    }

    public void guardar(Cuenta cuenta) {

        String sentencia = """
                INSERT INTO cuentas (numero_cuenta, cliente_id, saldo, fecha_creacion)
                VALUES (?, ?, ?, ?)
                """;

        try (Connection conn = DatabaseConnectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sentencia, Statement.RETURN_GENERATED_KEYS)) {

            String ibanGenerado = generarIBAN(conn);


            cuenta.setIban(ibanGenerado);
            stmt.setString(1, ibanGenerado);
            stmt.setLong(2, cuenta.getCliente_id());
            stmt.setDouble(3, cuenta.getSaldo());
            stmt.setTimestamp(4, Timestamp.valueOf(cuenta.getFechaCreacion()));
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                cuenta.setId(rs.getLong(1));
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

    public Cuenta buscarPorIban(String iban) {
        Cuenta encontrada = null;
        String sentencia1 = """
                SELECT * FROM cuentas WHERE numero_cuenta = ?
                """;

        try (Connection conn = DatabaseConnectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sentencia1)) {

            stmt.setString(1,iban);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {

                encontrada = new Cuenta();
                encontrada.setIban(iban);
                encontrada.setFechaCreacion(rs.getTimestamp("fecha_creacion").toLocalDateTime());
                encontrada.setCliente_id(rs.getLong("cliente_id"));
                encontrada.setId(rs.getLong("id"));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return encontrada;
    }

}
