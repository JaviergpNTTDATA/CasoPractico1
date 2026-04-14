package org.javinttdata.config;

import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class DatabaseConnectionManagerTest {

    @Test
    void debeDevolverMismaInstancia() {
        DatabaseConnectionManager instance1 = DatabaseConnectionManager.getInstance();
        DatabaseConnectionManager instance2 = DatabaseConnectionManager.getInstance();

        assertNotNull(instance1);
        assertSame(instance1, instance2);
    }

    @Test
    void debeCrearDiferentesConexiones() throws SQLException {
        Connection connection1 = DatabaseConnectionManager.getInstance().getConnection();
        Connection connection2 = DatabaseConnectionManager.getInstance().getConnection();

        assertNotNull(connection1);
        assertNotNull(connection2);
        assertNotSame(connection1, connection2);
        connection1.close();
        connection2.close();
    }

    @Test
    void deveDeSerConexionValida() throws SQLException {
        try (Connection connection = DatabaseConnectionManager.getInstance().getConnection()) {
            assertTrue(connection.isValid(3)); //Ponemos un timeout de 3 segundo
        }
    }
}
