package org.javinttdata.operacion.repository;

import org.javinttdata.config.DatabaseConnectionManager;
import org.javinttdata.operacion.model.Operacion;
import org.javinttdata.operacion.model.enums.TipoOperacion;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class OperacionesRepository {

    public void guardar(int cuentaId, TipoOperacion tipo, BigDecimal cantidad) {

        String sql = """
                INSERT INTO movimientos (cuenta_id, tipo, cantidad)
                VALUES (?, ?, ?)
                """;

        try (Connection conn = DatabaseConnectionManager.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, cuentaId);
            stmt.setString(2, tipo.name());
            stmt.setBigDecimal(3, cantidad);

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error al guardar movimiento", e);
        }
    }

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
    public List<Operacion> obtenerMovimientosPorCuenta(String iban) {

        List<Operacion> lista = new ArrayList<>();

        String sql = """
                SELECT m.*
                FROM movimientos m
                JOIN cuentas c ON m.cuenta_id = c.id
                WHERE c.numero_cuenta = ?
                ORDER BY m.fecha DESC
                """;

        try (Connection conn = DatabaseConnectionManager.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, iban);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {

                Operacion op = new Operacion(
                        TipoOperacion.valueOf(rs.getString("tipo")),
                        rs.getBigDecimal("cantidad")
                );

                op.setFecha(
                        rs.getTimestamp("fecha")
                                .toLocalDateTime());

                lista.add(op);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener movimientos", e);
        }

        return lista;
    }

    public List<Operacion> obtenerMovimientosPorFechas(String iban, LocalDate inicio, LocalDate fin) {

        List<Operacion> lista = new ArrayList<>();

        String sql = """
                SELECT m.*
                FROM movimientos m
                JOIN cuentas c ON m.cuenta_id = c.id
                WHERE c.numero_cuenta = ?
                  AND DATE(m.fecha) BETWEEN ? AND ?
                ORDER BY m.fecha DESC
                """;

        try (Connection conn = DatabaseConnectionManager.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, iban);
            stmt.setDate(2, Date.valueOf(inicio));
            stmt.setDate(3, Date.valueOf(fin));

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {

                Operacion op = new Operacion(
                        TipoOperacion.valueOf(rs.getString("tipo")),
                        rs.getBigDecimal("cantidad")
                );

                op.setFecha(
                        rs.getTimestamp("fecha")
                                .toLocalDateTime());

                lista.add(op);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al filtrar movimientos", e);
        }

        return lista;
    }
}

