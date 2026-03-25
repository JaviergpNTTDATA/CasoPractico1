package org.javinttdata.operacion;

import org.javinttdata.cliente.model.Cliente;
import org.javinttdata.cuenta.model.Cuenta;
import org.javinttdata.operacion.model.Operacion;
import org.javinttdata.operacion.model.enums.TipoOperacion;
import org.javinttdata.operacion.repository.OperacionesRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Clase que gestiona los test de operacion
 */
class OperacionServiceTest {
    //Declaramos repositorio y cuenta que usaremos en los test
    private Cuenta cuenta;
    private OperacionesRepository repoOp;

    /**
     * Antes de cada test creamos un cliente para usarlo de prueba
     */
    @BeforeEach
    void setUp() {
        Cliente cliente = new Cliente("Test", "User", "12345678X", "test@mail.com", "900100200");
        cuenta = new Cuenta(cliente);
        repoOp = new OperacionesRepository();
    }

    /**
     * Este metodo comprueba que se ejecute correctamente el deposito
     */
    @Test
    void testDepositoExitoso() {
        double cantidad = 500.0;
        cuenta.ingresar(cantidad);

        //Simulamos que se hacer un operacion
        repoOp.operaciones.computeIfAbsent(cuenta.getIban(), k -> new java.util.ArrayList<>())
                .add(new Operacion(TipoOperacion.Deposito, cantidad));

        //Comparamos el saldo
        assertEquals(500.0, cuenta.getSaldo());
        //El tamaño de la lista
        assertEquals(1, repoOp.operaciones.get(cuenta.getIban()).size());
        //Y el tipo de operacion
        assertEquals(TipoOperacion.Deposito, repoOp.operaciones.get(cuenta.getIban()).get(0).getTipoO());
    }
}