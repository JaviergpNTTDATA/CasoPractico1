package org.javinttdata.operacion.service;

import org.javinttdata.config.DatabaseConnectionManager;
import org.javinttdata.cuenta.model.Cuenta;
import org.javinttdata.cuenta.repository.CuentaRepository;
import org.javinttdata.operacion.model.enums.TipoOperacion;
import org.javinttdata.operacion.repository.OperacionesRepository;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

public class OperacionService {

    private final CuentaRepository cuentaRepository;
    private final OperacionesRepository operacionesRepository;

    public OperacionService(CuentaRepository cuentaRepository,
                            OperacionesRepository operacionesRepository) {
        this.cuentaRepository = cuentaRepository;
        this.operacionesRepository = operacionesRepository;
    }

    // =========================
    // DEPOSITO
    // =========================
    public Cuenta depositar(String iban, BigDecimal cantidad) {

        if (cantidad == null || cantidad.compareTo(BigDecimal.ZERO) <= 0)
            return null;

        try (Connection conn = DatabaseConnectionManager.getInstance().getConnection()) {

            Optional<Cuenta> cuentaOpt =
                    cuentaRepository.buscarPorNumero(iban, conn);

            if (cuentaOpt.isEmpty())
                return null;

            Cuenta cuenta = cuentaOpt.get();

            BigDecimal nuevoSaldo = cuenta.getSaldo().add(cantidad);

            cuentaRepository.actualizarSaldo(
                    cuenta.getId(),
                    nuevoSaldo,
                    conn
            );

            operacionesRepository.guardar(conn,
                    Math.toIntExact(cuenta.getId()),
                    TipoOperacion.DEPOSITO,
                    cantidad);

            cuenta.setSaldo(nuevoSaldo);

            return cuenta;

        } catch (Exception e) {
            throw new RuntimeException("Error en depósito", e);
        }
    }

    // =========================
    // RETIRO
    // =========================
    public Cuenta retirar(String iban, BigDecimal cantidad) {

        if (cantidad == null || cantidad.compareTo(BigDecimal.ZERO) <= 0)
            return null;

        try (Connection conn = DatabaseConnectionManager.getInstance().getConnection()) {

            Optional<Cuenta> cuentaOpt =
                    cuentaRepository.buscarPorNumero(iban, conn);

            if (cuentaOpt.isEmpty())
                return null;

            Cuenta cuenta = cuentaOpt.get();

            if (cuenta.getSaldo().compareTo(cantidad) < 0)
                return null;

            BigDecimal nuevoSaldo =
                    cuenta.getSaldo().subtract(cantidad);

            cuentaRepository.actualizarSaldo(
                    cuenta.getId(),
                    nuevoSaldo,
                    conn
            );

            operacionesRepository.guardar(conn,
                    Math.toIntExact(cuenta.getId()),
                    TipoOperacion.RETIRO,
                    cantidad);

            cuenta.setSaldo(nuevoSaldo);

            return cuenta;

        } catch (Exception e) {
            throw new RuntimeException("Error en retiro", e);
        }
    }

    // =========================
    // TRANSFERENCIA
    // =========================
    public void transferir(String numeroCuentaOrigen,
                           String numeroCuentaDestino,
                           BigDecimal importe) {

        if (importe == null || importe.compareTo(BigDecimal.ZERO) <= 0)
            throw new IllegalArgumentException("Importe inválido");

        if (numeroCuentaOrigen.equals(numeroCuentaDestino))
            throw new IllegalArgumentException("No se puede transferir a la misma cuenta");

        try (Connection conn = DatabaseConnectionManager.getInstance().getConnection()) {

            conn.setAutoCommit(false);

            try {

                Optional<Cuenta> origenOpt =
                        cuentaRepository.buscarPorNumero(numeroCuentaOrigen, conn);

                Optional<Cuenta> destinoOpt =
                        cuentaRepository.buscarPorNumero(numeroCuentaDestino, conn);

                if (origenOpt.isEmpty())
                    throw new RuntimeException("Cuenta origen no encontrada");

                if (destinoOpt.isEmpty())
                    throw new RuntimeException("Cuenta destino no encontrada");

                Cuenta origen = origenOpt.get();
                Cuenta destino = destinoOpt.get();

                if (origen.getSaldo().compareTo(importe) < 0)
                    throw new RuntimeException("Saldo insuficiente");

                BigDecimal nuevoSaldoOrigen =
                        origen.getSaldo().subtract(importe);

                BigDecimal nuevoSaldoDestino =
                        destino.getSaldo().add(importe);

                cuentaRepository.actualizarSaldo(
                        origen.getId(),
                        nuevoSaldoOrigen,
                        conn
                );

                cuentaRepository.actualizarSaldo(
                        destino.getId(),
                        nuevoSaldoDestino,
                        conn
                );

                operacionesRepository.guardar(conn,
                        Math.toIntExact(origen.getId()),
                        TipoOperacion.TRANSFERENCIA_SALIENTE,
                        importe);

                operacionesRepository.guardar(conn,
                        Math.toIntExact(destino.getId()),
                        TipoOperacion.TRANSFERENCIA_ENTRANTE,
                        importe);

                conn.commit();

            } catch (Exception e) {
                conn.rollback();
                throw new RuntimeException("Error en la transferencia", e);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error de conexión", e);
        }
    }
}
