package org.javinttdata.cuenta.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MetodosIban {
    public static String obtenerSiguienteSecuencia(Connection conn) throws SQLException {

        String sql = "SELECT nextval('iban_seq')";

        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                return String.format("%012d", rs.getLong(1));
            }
        }

        throw new SQLException("No se pudo obtener la secuencia IBAN");
    }
    public static String generarIBAN(Connection conn) throws SQLException {

        String secuencial = obtenerSiguienteSecuencia(conn);
        return "ES91210000" + secuencial;
    }


}
