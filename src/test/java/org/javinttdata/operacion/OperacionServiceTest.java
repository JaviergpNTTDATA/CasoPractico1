package org.javinttdata.operacion;

import org.javinttdata.cliente.model.Cliente;
import org.javinttdata.cuenta.model.Cuenta;
import org.javinttdata.operacion.model.Operacion;
import org.javinttdata.operacion.model.enums.TipoOperacion;
import org.javinttdata.operacion.repository.OperacionesRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

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
        cuenta = new Cuenta(cliente.getId());
        repoOp = new OperacionesRepository();
    }
}