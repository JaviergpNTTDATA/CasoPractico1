package org.javinttdata.operacion.repository;

import org.javinttdata.config.DatabaseConnectionManager;
import org.javinttdata.operacion.model.enums.TipoOperacion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class OperacionesRepository {

    // ✅ Versión simple (no transaccional)
    public void guardar(int cuentaId,
                        TipoOperacion tipo,
                        double cantidad) {

        String sql = """
                INSERT INTO movimientos (cuenta_id, tipo, cantidad)
                VALUES (?, ?, ?)
                """;

        try (Connection conn = DatabaseConnectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, cuentaId);
            stmt.setString(2, tipo.name());
            stmt.setDouble(3, cantidad);

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error al guardar movimiento", e);
        }
    }

    // ✅ Versión para usar dentro de transacción
    public void guardar(Connection conn,
                        int cuentaId,
                        TipoOperacion tipo,
                        double cantidad) {

        String sql = """
                INSERT INTO movimientos (cuenta_id, tipo, cantidad)
                VALUES (?, ?, ?)
                """;

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, cuentaId);
            stmt.setString(2, tipo.name());
            stmt.setDouble(3, cantidad);

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error al guardar movimiento", e);
        }
    }
}
