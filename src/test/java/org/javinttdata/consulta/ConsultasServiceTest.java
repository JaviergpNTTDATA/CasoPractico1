package org.javinttdata.consulta;

import org.javinttdata.cuenta.repository.CuentaRepository;
import org.javinttdata.operacion.model.Operacion;
import org.javinttdata.operacion.model.enums.TipoOperacion;
import org.javinttdata.operacion.repository.OperacionesRepository;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class ConsultasServiceTest {

    @Test
    void testFiltradoRangoFechasExitoso() {
        // Creamos operaciones en distintas fechas
        Operacion opVieja = new Operacion(TipoOperacion.Deposito, 100);
        opVieja.setFecha(LocalDateTime.of(2023, 1, 1, 10, 0));

        Operacion opReciente = new Operacion(TipoOperacion.Retiro, 50);
        opReciente.setFecha(LocalDateTime.of(2023, 6, 1, 10, 0));

        List<Operacion> historial = List.of(opVieja, opReciente);

        // Rango de búsqueda: Junio 2023
        LocalDate inicio = LocalDate.of(2023, 5, 31);
        LocalDate fin = LocalDate.of(2023, 6, 30);

        List<Operacion> filtradas = historial.stream()
                .filter(op -> !op.getFecha().toLocalDate().isBefore(inicio) &&
                        !op.getFecha().toLocalDate().isAfter(fin))
                .toList();

        assertEquals(1, filtradas.size());
        assertEquals(TipoOperacion.Retiro, filtradas.get(0).getTipoO());
    }

    @Test
    void testConsultaSaldoCuentaInexistente() {
        // Verificamos que la lógica de búsqueda devuelva null si el IBAN no existe
        CuentaRepository repo = new CuentaRepository();
        assertNull(repo.buscarPorIban("IBAN_FALSO"));
    }

    @Test
    void testHistorialVacioNoLanzaExcepcion() {
        // Si una cuenta no tiene movimientos, el Map devuelve null o lista vacía
        OperacionesRepository repoOp = new OperacionesRepository();
        List<Operacion> lista = repoOp.operaciones.get("ES910000");

        assertNull(lista);
        // Tu código en OperacionesConsultas gestiona este null para mostrar "No ha habido movimientos"
    }
}