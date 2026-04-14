package org.javinttdata.operacion.repository;

import org.javinttdata.config.DatabaseConnectionManager;
import org.javinttdata.operacion.model.Operacion;
import org.javinttdata.operacion.model.enums.TipoOperacion;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class OperacionesRepositoryJdbc implements OperacionRepository {

    /**
     * Metodo que usamos para guardar la operacion
     * @param conn
     * @param cuentaId
     * @param tipo
     * @param cantidad
     */
    public void guardar(Connection conn, int cuentaId, TipoOperacion tipo, BigDecimal cantidad) {

        String sql = """
                INSERT INTO movimientos (cuenta_id, tipo, cantidad)
                VALUES (?, ?, ?)
                """;

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, cuentaId);
            stmt.setString(2, tipo.name());
            stmt.setBigDecimal(3, cantidad);

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error al guardar movimiento", e);
        }
    }
    @Override
    public Operacion guardar(Operacion movimiento) {
        throw new UnsupportedOperationException(
                "Debe usarse guardar(cuentaId, tipo, cantidad)"
        );
    }

    @Override
    public Operacion guardar(Operacion movimiento, Connection conn) {
        throw new UnsupportedOperationException(
        );
    }

    /**
     * Metodo que busca las operaciones de una cuenta por el id
     * @param cuentaId
     * @return
     */
    @Override
    public List<Operacion> buscarPorCuentaId(Long cuentaId) {

        List<Operacion> lista = new ArrayList<>();

        String sql = """
                SELECT * FROM movimientos
                WHERE cuenta_id = ?
                ORDER BY fecha DESC
                """;

        try (Connection conn = DatabaseConnectionManager.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, cuentaId);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                lista.add(creacionOp(rs));
            }

            return lista;

        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar movimientos", e);
        }
    }

    /**
     * Metodo que busca las operaciones en un rango de fechas
     * @param cuentaId
     * @param inicio
     * @param fin
     * @return
     */
    @Override
    public List<Operacion> buscarPorCuentaIdYFechas(Long cuentaId, LocalDateTime inicio, LocalDateTime fin) {

        List<Operacion> lista = new ArrayList<>();

        String sql = """
                SELECT * FROM movimientos
                WHERE cuenta_id = ?
                  AND fecha BETWEEN ? AND ?
                ORDER BY fecha DESC
                """;

        try (Connection conn = DatabaseConnectionManager.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, cuentaId);
            stmt.setTimestamp(2, Timestamp.valueOf(inicio));
            stmt.setTimestamp(3, Timestamp.valueOf(fin));

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                lista.add(creacionOp(rs));
            }

            return lista;

        } catch (SQLException e) {
            throw new RuntimeException("Error al filtrar movimientos", e);
        }
    }


    private Operacion creacionOp(ResultSet rs) throws SQLException {

        Operacion op = new Operacion(
                TipoOperacion.valueOf(rs.getString("tipo")),
                rs.getBigDecimal("cantidad")
        );

        op.setFecha(rs.getTimestamp("fecha").toLocalDateTime());

        return op;
    }
    public void guardar(Connection conn, int cuentaId, Operacion operacion) {

        String sql = """
            INSERT INTO movimientos (cuenta_id, tipo, cantidad, fecha)
            VALUES (?, ?, ?, ?)
            """;

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, cuentaId);
            stmt.setString(2, operacion.getTipoO().name());
            stmt.setBigDecimal(3, operacion.getCantidad());
            stmt.setTimestamp(4, Timestamp.valueOf(operacion.getFecha()));

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error al guardar movimiento", e);
        }
    }

}
