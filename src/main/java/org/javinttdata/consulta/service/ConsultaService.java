package org.javinttdata.consulta.service;

import org.javinttdata.cuenta.model.Cuenta;
import org.javinttdata.cuenta.repository.CuentaRepository;
import org.javinttdata.operacion.model.Operacion;
import org.javinttdata.operacion.repository.OperacionRepository;
import org.javinttdata.operacion.repository.OperacionesRepositoryJdbc;

import java.time.LocalDate;
import java.util.List;

public class ConsultaService {

    private final CuentaRepository cuentaRepository;
    private final OperacionRepository operacionesRepository;

    public ConsultaService(CuentaRepository cuentaRepository, OperacionRepository operacionesRepository) {
        this.cuentaRepository = cuentaRepository;
        this.operacionesRepository = operacionesRepository;
    }

    public Cuenta consultarSaldo(String iban) {
        return cuentaRepository.buscarPorNumero(iban).orElse(null);
    }

    public List<Operacion> historialMovimientos(String iban) {

        return cuentaRepository.buscarPorNumero(iban)
                .map(cuenta -> operacionesRepository
                        .buscarPorCuentaId(cuenta.getId()))
                .orElse(null);
    }

    public List<Operacion> movimientosPorFecha(String iban, LocalDate inicio, LocalDate fin) {

        return cuentaRepository.buscarPorNumero(iban)
                .map(cuenta -> operacionesRepository
                        .buscarPorCuentaIdYFechas(
                                cuenta.getId(),
                                inicio.atStartOfDay(),
                                fin.atTime(23, 59, 59)))
                .orElse(null);
    }

}
