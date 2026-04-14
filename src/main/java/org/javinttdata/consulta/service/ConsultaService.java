package org.javinttdata.consulta.service;

import org.javinttdata.cuenta.model.Cuenta;
import org.javinttdata.cuenta.repository.CuentaRepository;
import org.javinttdata.operacion.model.Operacion;
import org.javinttdata.operacion.repository.OperacionesRepository;

import java.time.LocalDate;
import java.util.List;

public class ConsultaService {

    private final CuentaRepository cuentaRepository;
    private final OperacionesRepository operacionesRepository;

    public ConsultaService(CuentaRepository cuentaRepository,
                           OperacionesRepository operacionesRepository) {
        this.cuentaRepository = cuentaRepository;
        this.operacionesRepository = operacionesRepository;
    }

    public Cuenta consultarSaldo(String iban) {
        return cuentaRepository.buscarPorIban(iban);
    }

    public List<Operacion> historialMovimientos(String iban) {

        Cuenta cuenta = cuentaRepository.buscarPorIban(iban);
        if (cuenta == null) return null;

        return operacionesRepository.obtenerMovimientosPorCuenta(iban);
    }

    public List<Operacion> movimientosPorFecha(String iban,
                                               LocalDate inicio,
                                               LocalDate fin) {

        Cuenta cuenta = cuentaRepository.buscarPorIban(iban);
        if (cuenta == null) return null;

        return operacionesRepository.obtenerMovimientosPorFechas(
                iban, inicio, fin);
    }
}
