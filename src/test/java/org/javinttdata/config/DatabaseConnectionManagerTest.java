package org.javinttdata.config;

import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class DatabaseConnectionManagerTest {

    @Test
    void shouldReturnSameSingletonInstance() {
        DatabaseConnectionManager instance1 = DatabaseConnectionManager.getInstance();
        DatabaseConnectionManager instance2 = DatabaseConnectionManager.getInstance();

        assertNotNull(instance1);
        assertSame(instance1, instance2); // Verifica patrón Singleton
    }

    @Test
    void shouldCreateNewConnectionEachTime() throws SQLException {
        Connection connection1 = DatabaseConnectionManager.getConnection();
        Connection connection2 = DatabaseConnectionManager.getConnection();

        assertNotNull(connection1);
        assertNotNull(connection2);
        assertNotSame(connection1, connection2); // Deben ser conexiones distintas

        connection1.close();
        connection2.close();
    }

    @Test
    void connectionShouldBeValid() throws SQLException {
        try (Connection connection = DatabaseConnectionManager.getConnection()) {
            assertTrue(connection.isValid(2)); // Timeout 2 segundos
        }
    }
}
