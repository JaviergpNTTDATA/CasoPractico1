package org.javinttdata.cuenta.repository;

import org.javinttdata.config.DatabaseConnectionManager;
import org.javinttdata.cuenta.model.Cuenta;
import org.javinttdata.cuenta.model.CuentaBuilder;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.javinttdata.cuenta.repository.MetodosIban.generarIBAN;

public class CuentaRepositoryJdbc implements CuentaRepository {

    /**
     * Metodo que guarda las cuentas en la bd
     * @param cuenta
     * @return cuenta creada
     */
    @Override
    public Cuenta guardar(Cuenta cuenta) {

        String sql = """
                INSERT INTO cuentas (numero_cuenta, cliente_id, saldo, fecha_creacion)
                VALUES (?, ?, ?, ?)
                """;

        try (Connection conn = DatabaseConnectionManager.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            String ibanGenerado = generarIBAN(conn);

            cuenta.setIban(ibanGenerado);

            stmt.setString(1, ibanGenerado);
            stmt.setLong(2, cuenta.getCliente_id());
            stmt.setBigDecimal(3, cuenta.getSaldo());
            stmt.setTimestamp(4, Timestamp.valueOf(cuenta.getFechaCreacion()));

            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                cuenta.setId(rs.getLong(1));
            }

            return cuenta;

        } catch (SQLException e) {
            throw new RuntimeException("Error al guardar cuenta", e);
        }
    }

    /**
     * Metodo que busca una cuenta por su id
     * @param id
     * @return cuenta encontrada
     */
    @Override
    public Optional<Cuenta> buscarPorId(Long id) {

        String sql = "SELECT * FROM cuentas WHERE id = ?";

        try (Connection conn = DatabaseConnectionManager.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return Optional.of(creadorCuenta(rs));
            }

            return Optional.empty();

        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar cuenta por ID", e);
        }
    }

    /**
     * Metodo para encintrar cuenta por iban, no para transacciones
     * @param numeroCuenta
     * @return cuenta encontrada
     */
    @Override
    public Optional<Cuenta> buscarPorNumero(String numeroCuenta) {

        String sql = "SELECT * FROM cuentas WHERE numero_cuenta = ?";

        try (Connection conn = DatabaseConnectionManager.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, numeroCuenta);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return Optional.of(creadorCuenta(rs));
            }

            return Optional.empty();

        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar cuenta por número", e);
        }
    }

    /**
     * Metodo que sirve para encontrar cuentas, pero en este caso para las transacciones
     * @param numeroCuenta
     * @param conn
     * @return devuelve cuenta encontrada
     */
    @Override
    public Optional<Cuenta> buscarPorNumero(String numeroCuenta, Connection conn) {

        String sql = "SELECT * FROM cuentas WHERE numero_cuenta = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, numeroCuenta);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return Optional.of(creadorCuenta(rs));
            }

            return Optional.empty();

        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar cuenta por número", e);
        }
    }

    /**
     * Metodo que se encarga de buscar las cuentas que tiene un cliente por su id
     * @param clienteId
     * @return lista de cuentas
     */
    @Override
    public List<Cuenta> buscarPorClienteId(Long clienteId) {

        List<Cuenta> lista = new ArrayList<>();

        String sql = "SELECT * FROM cuentas WHERE cliente_id = ?";

        try (Connection conn = DatabaseConnectionManager.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, clienteId);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                lista.add(creadorCuenta(rs));
            }

            return lista;

        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar cuentas del cliente", e);
        }
    }

    /**
     * Este metodo actualiza el saldo, no lo uso, porque lo tengo porgramado con los metodos de las clases
     * @param cuentaId
     * @param nuevoSaldo
     * @return
     */
    @Override
    public Cuenta actualizarSaldo(Long cuentaId, BigDecimal nuevoSaldo) {

        String sql = "UPDATE cuentas SET saldo = ? WHERE id = ?";

        try (Connection conn = DatabaseConnectionManager.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setBigDecimal(1, nuevoSaldo);
            stmt.setLong(2, cuentaId);

            stmt.executeUpdate();

            return buscarPorId(cuentaId).orElseThrow();

        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar saldo", e);
        }
    }

    /**
     * Este metodo actualiza el saldo, no lo uso, porque lo tengo porgramado con los metodos de las clases
     * @param cuentaId
     * @param nuevoSaldo
     * @return
     */
    @Override
    public Cuenta actualizarSaldo(Long cuentaId, BigDecimal nuevoSaldo, Connection conn) {

        String sql = "UPDATE cuentas SET saldo = ? WHERE id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setBigDecimal(1, nuevoSaldo);
            stmt.setLong(2, cuentaId);

            stmt.executeUpdate();

            return buscarPorNumero(
                    buscarPorId(cuentaId).orElseThrow().getIban(),
                    conn
            ).orElseThrow();

        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar saldo", e);
        }
    }

    /**
     * Metodo que nos ayuda a crear una cuenta con un builder,
     * Por tanto uso de estas sentencias, he creado un metodo para llamarlo directamente
     * @param rs
     * @return
     * @throws SQLException
     */
    private Cuenta creadorCuenta(ResultSet rs) throws SQLException {
        return new CuentaBuilder()
                .id(rs.getLong("id"))
                .iban(rs.getString("numero_cuenta"))
                .clienteId(rs.getLong("cliente_id"))
                .saldo(rs.getBigDecimal("saldo"))
                .fechaCreacion(rs.getTimestamp("fecha_creacion").toLocalDateTime())
                .build();
    }
}
