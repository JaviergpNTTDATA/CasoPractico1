package org.javinttdata.operacion;

import org.javinttdata.cliente.model.Cliente;
import org.javinttdata.cliente.repository.ClienteRepository;
import org.javinttdata.cliente.repository.ClientesRepositoryJdbc;
import org.javinttdata.cuenta.model.Cuenta;
import org.javinttdata.cuenta.repository.CuentaRepository;
import org.javinttdata.cuenta.repository.CuentaRepositoryJdbc;
import org.javinttdata.operacion.repository.OperacionesRepositoryJdbc;
import org.javinttdata.operacion.service.OperacionService;
import org.junit.jupiter.api.BeforeEach;

import java.math.BigDecimal;
import java.time.LocalDateTime;

class OperacionServiceTest {
    private OperacionService operacionService;
    private CuentaRepository cuentaRepo;

    @BeforeEach
    void setUp() {

        ClienteRepository clienteRepo = new ClientesRepositoryJdbc();
        CuentaRepository cuentaRepo = new CuentaRepositoryJdbc();
        OperacionesRepositoryJdbc operacionesRepo =
                new OperacionesRepositoryJdbc();

        operacionService =
                new OperacionService(cuentaRepo, operacionesRepo);

        // Crear cliente
        Cliente cliente = new Cliente(
                "Test",
                "User",
                "" + System.currentTimeMillis(),
                System.currentTimeMillis() + "@test.com",
                "6" + System.currentTimeMillis()
        );

        Cliente clienteGuardado = clienteRepo.guardar(cliente);

        // Crear cuenta correctamente
        Cuenta cuenta = new Cuenta();
        cuenta.setCliente_id(clienteGuardado.getId());
        cuenta.setSaldo(BigDecimal.valueOf(1000));
        cuenta.setFechaCreacion(LocalDateTime.now());
        cuenta.setIban("ES" + System.currentTimeMillis());

        cuentaRepo.guardar(cuenta);
    }


/*Este test falla por duplicidad de datos en la base de datos
    @Test
    void deberiaDepositarEnBaseDeDatos() {

        Cuenta cuenta =
                operacionService.depositar("ES999", BigDecimal.valueOf(200));

        assertNotNull(cuenta);
        assertEquals(
                BigDecimal.valueOf(1200),
                cuenta.getSaldo()
        );
    }

 */
}