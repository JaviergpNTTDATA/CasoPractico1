package org.javinttdata.cuenta;

import org.javinttdata.cliente.model.Cliente;
import org.javinttdata.cuenta.model.Cuenta;
import org.javinttdata.cuenta.repository.CuentaRepository;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CuentaServiceTest {
    @Test
    void deberiaGenerarIbanCorrecto() {
        Cliente titular = new Cliente("Carlos", "Ruiz", "11223344C", "carlos@test.com", "622222222");
        Cuenta cuenta = new Cuenta(titular); //

        assertTrue(cuenta.getIban().startsWith("ES91210000")); // Formato definido en Cuenta
        assertEquals(0, cuenta.getSaldo()); // Saldo inicial debe ser 0
    }

    @Test
    void deberiaFiltrarCuentasPorClienteId() {
        CuentaRepository repo = new CuentaRepository();
        Cliente c = new Cliente("Luis", "Sosa", "55667788D", "luis@test.com", "633333333");
        c.setId(1001L);

        Cuenta cuenta = new Cuenta(c);
        repo.guardar(cuenta);

        assertEquals(1, repo.buscarPorIdCliente(1001L).size());
    }
}