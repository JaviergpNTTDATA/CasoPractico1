package org.javinttdata.consulta.service;

import org.javinttdata.cuenta.model.Cuenta;
import org.javinttdata.cuenta.repository.CuentaRepositoryJdbc;
import org.javinttdata.operacion.model.Operacion;
import org.javinttdata.operacion.repository.OperacionesRepositoryJdbc;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class ConsultaService {

<<<<<<< HEAD
    private final CuentaRepositoryJdbc cuentaRepository;
    private final OperacionesRepository operacionesRepository;

    public ConsultaService(CuentaRepositoryJdbc cuentaRepository,
                           OperacionesRepository operacionesRepository) {
=======
    private final CuentaRepository cuentaRepository;
    private final OperacionesRepositoryJdbc operacionesRepository;

    public ConsultaService(CuentaRepository cuentaRepository, OperacionesRepositoryJdbc operacionesRepository) {
>>>>>>> 4660014 (Commit final de la capa operacion(a priori))
        this.cuentaRepository = cuentaRepository;
        this.operacionesRepository = operacionesRepository;
    }

    public Cuenta consultarSaldo(String iban) {
        return cuentaRepository.buscarPorNumero(iban).orElse(null);
    }

    public List<Operacion> historialMovimientos(String iban) {

        Cuenta cuenta = cuentaRepository.buscarPorNumero(iban).orElse(null);
        if (cuenta == null) return null;

        return operacionesRepository
                .buscarPorCuentaId(cuenta.getId());
    }


    public List<Operacion> movimientosPorFecha(String iban,
                                               LocalDate inicio,
                                               LocalDate fin) {

        Cuenta cuenta = cuentaRepository.buscarPorNumero(iban).orElse(null);
        if (cuenta == null) return null;

        return operacionesRepository.buscarPorCuentaIdYFechas(
                cuenta.getId(),
                inicio.atStartOfDay(),
                fin.atTime(23, 59, 59)
        );
    }
}
