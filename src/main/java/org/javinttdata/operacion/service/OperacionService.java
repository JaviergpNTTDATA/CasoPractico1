package org.javinttdata.operacion.service;

import org.javinttdata.config.DatabaseConnectionManager;
import org.javinttdata.cuenta.model.Cuenta;
import org.javinttdata.cuenta.repository.CuentaRepository;
import org.javinttdata.operacion.model.enums.TipoOperacion;
import org.javinttdata.operacion.repository.OperacionesRepository;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;

public class OperacionService {

    private final CuentaRepository cuentaRepository;
    private final OperacionesRepository operacionesRepository;

    public OperacionService(CuentaRepository cuentaRepository,
                            OperacionesRepository operacionesRepository) {
        this.cuentaRepository = cuentaRepository;
        this.operacionesRepository = operacionesRepository;
    }

    public Cuenta depositar(String iban, BigDecimal cantidad) {

        if (cantidad == null || cantidad.compareTo(BigDecimal.ZERO) <= 0)
            return null;

        try (Connection conn = DatabaseConnectionManager.getInstance().getConnection()) {

            Cuenta cuenta = cuentaRepository.buscarPorIban(conn, iban);
            if (cuenta == null) return null;

            cuenta.ingresar(cantidad);

            cuentaRepository.actualizarSaldo(conn, cuenta);

            operacionesRepository.guardar(conn,
                    Math.toIntExact(cuenta.getId()),
                    TipoOperacion.DEPOSITO,
                    cantidad);

            return cuenta;

        } catch (Exception e) {
            throw new RuntimeException("Error en depósito", e);
        }
    }

    public Cuenta retirar(String iban, BigDecimal cantidad) {

        if (cantidad == null || cantidad.compareTo(BigDecimal.ZERO) <= 0)
            return null;

        try (Connection conn = DatabaseConnectionManager.getInstance().getConnection()) {

            Cuenta cuenta = cuentaRepository.buscarPorIban(conn, iban);

            if (cuenta == null ||
                    cuenta.getSaldo().compareTo(cantidad) < 0)
                return null;

            cuenta.retirar(cantidad);

            cuentaRepository.actualizarSaldo(conn, cuenta);

            operacionesRepository.guardar(conn,
                    Math.toIntExact(cuenta.getId()),
                    TipoOperacion.RETIRO,
                    cantidad);

            return cuenta;

        } catch (Exception e) {
            throw new RuntimeException("Error en retiro", e);
        }
    }

    public void transferir(String numeroCuentaOrigen,
                           String numeroCuentaDestino,
                           BigDecimal importe) {

        if (importe == null || importe.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Importe inválido");
        }

        if (numeroCuentaOrigen.equals(numeroCuentaDestino)) {
            throw new IllegalArgumentException("No se puede transferir a la misma cuenta");
        }

        try (Connection conn = DatabaseConnectionManager.getInstance().getConnection()) {

            conn.setAutoCommit(false);

            try {

                Cuenta origen = cuentaRepository
                        .buscarPorIban(conn, numeroCuentaOrigen);

                if (origen == null)
                    throw new RuntimeException("Cuenta origen no encontrada");

                Cuenta destino = cuentaRepository
                        .buscarPorIban(conn, numeroCuentaDestino);

                if (destino == null)
                    throw new RuntimeException("Cuenta destino no encontrada");

                if (origen.getSaldo().compareTo(importe) < 0)
                    throw new RuntimeException("Saldo insuficiente");

                origen.setSaldo(origen.getSaldo().subtract(importe));
                destino.setSaldo(destino.getSaldo().add(importe));

                cuentaRepository.actualizarSaldo(conn, origen);
                cuentaRepository.actualizarSaldo(conn, destino);

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
