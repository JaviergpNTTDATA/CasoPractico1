package org.javinttdata.consulta;

import org.javinttdata.cuenta.repository.CuentaRepository;
import org.javinttdata.operacion.model.Operacion;
import org.javinttdata.operacion.model.enums.TipoOperacion;
import org.javinttdata.operacion.repository.OperacionesRepository;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Clase para los test de consulta
 */
class ConsultasServiceTest {

    /**
     * Test que valida el funcionamiento de busqueda por fechas
     */
    @Test
    void testFiltradoRangoFechasExitoso() {
        //Creamos dos fechas para hacer las pruebas
        Operacion opVieja = new Operacion(TipoOperacion.DEPOSITO, BigDecimal.valueOf(100));
        opVieja.setFecha(LocalDateTime.of(2023, 1, 1, 10, 0));

        Operacion opReciente = new Operacion(TipoOperacion.RETIRO, BigDecimal.valueOf(50));
        opReciente.setFecha(LocalDateTime.of(2023, 6, 1, 10, 0));

        List<Operacion> historial = List.of(opVieja, opReciente);

        //Buscamos solo en junio
        LocalDate inicio = LocalDate.of(2023, 5, 31);
        LocalDate fin = LocalDate.of(2023, 6, 30);

        List<Operacion> filtradas = historial.stream()
                .filter(op -> !op.getFecha().toLocalDate().isBefore(inicio) &&
                        !op.getFecha().toLocalDate().isAfter(fin))
                .toList();

        //Deberia de haber solo una operacion en junio
        assertEquals(1, filtradas.size());
        //Aqui comprobamos el tipo de la operacion para hacer un doble check
        assertEquals(TipoOperacion.RETIRO, filtradas.get(0).getTipoO());
    }

    /**
     * Test que verifica que si se da un iban incorrecto devuelva null y no una cuenta
     */
    @Test
    void testConsultaSaldoCuentaInexistente() {
        //Creamos el repo y le preguntamos por un iban que claramente no existe
        CuentaRepository repo = new CuentaRepository();
        assertNull(repo.buscarPorIban("IBAN_FALSO"));
    }
}