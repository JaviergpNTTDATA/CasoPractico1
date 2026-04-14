package org.javinttdata.cuenta.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

//Clase que nos ayuda a crear el iban, mediante una secuencia creada en la base de datos, por lo que va autimatizado en la base de datos
public class MetodosIban {
    /**
     * Metodo mediante el cual obtenemos el ultimo digito que da la secuencia
     * @param conn
     * @return secuencia obtenida
     * @throws SQLException
     */
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

    /**
     * Metodo que tras obtener el digito de la secunecia de la base de datos gnereamos el iban con el patron ya dado
     * @param conn
     * @return iban ya genreado
     * @throws SQLException
     */
    public static String generarIBAN(Connection conn) throws SQLException {

        String secuencial = obtenerSiguienteSecuencia(conn);
        return "ES91210000" + secuencial;
    }


}
