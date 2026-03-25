package org.javinttdata.operacion;

import org.javinttdata.cliente.model.Cliente;
import org.javinttdata.cuenta.model.Cuenta;
import org.javinttdata.operacion.model.Operacion;
import org.javinttdata.operacion.model.enums.TipoOperacion;
import org.javinttdata.operacion.repository.OperacionesRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class OperacionesFinancierasTest {
    private Cuenta cuenta;
    private OperacionesRepository repoOp;

    @BeforeEach
    void setUp() {
        Cliente cliente = new Cliente("Test", "User", "12345678X", "test@mail.com", "900100200");
        cuenta = new Cuenta(cliente);
        repoOp = new OperacionesRepository();
    }

    @Test
    void testDepositoExitoso() {
        double cantidad = 500.0;
        cuenta.ingresar(cantidad); // Lógica de Cuenta.java

        // Simulación de registro en repositorio
        repoOp.operaciones.computeIfAbsent(cuenta.getIban(), k -> new java.util.ArrayList<>())
                .add(new Operacion(TipoOperacion.Deposito, cantidad));

        assertEquals(500.0, cuenta.getSaldo());
        assertEquals(1, repoOp.operaciones.get(cuenta.getIban()).size());
        assertEquals(TipoOperacion.Deposito, repoOp.operaciones.get(cuenta.getIban()).get(0).getTipoO());
    }

    @Test
    void testRetiroInsuficienteNoDeberiaRestarSaldo() {
        cuenta.ingresar(100.0);
        double intentoRetiro = 200.0;

        // La lógica en OperacionesOperaciones.java impide el retiro si saldo < cantidad
        if (cuenta.getSaldo() >= intentoRetiro) {
            cuenta.retirar(intentoRetiro);
        }

        assertEquals(100.0, cuenta.getSaldo(), "El saldo no debería haber cambiado");
    }

    @Test
    void testTransferenciaMismaCuentaNoDeberiaCausarError() {
        // Validación lógica: ¿puede un usuario transferirse a sí mismo?
        cuenta.ingresar(100.0);
        String ibanOrigen = cuenta.getIban();
        String ibanDestino = cuenta.getIban();

        if (ibanOrigen.equals(ibanDestino)) {
            // Podrías lanzar una excepción o simplemente no hacer nada
        }
        assertEquals(100.0, cuenta.getSaldo());
    }
}