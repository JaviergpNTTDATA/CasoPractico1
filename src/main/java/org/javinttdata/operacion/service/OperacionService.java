package org.javinttdata.operacion.service;

import org.javinttdata.config.DatabaseConnectionManager;
import org.javinttdata.cuenta.model.Cuenta;
import org.javinttdata.cuenta.repository.CuentaRepository;
import org.javinttdata.operacion.model.enums.TipoOperacion;
import org.javinttdata.operacion.repository.OperacionesRepository;

import java.sql.Connection;

public class OperacionService {

    private final CuentaRepository cuentaRepository;
    private final OperacionesRepository operacionesRepository;

    public OperacionService(CuentaRepository cuentaRepository,
                            OperacionesRepository operacionesRepository) {
        this.cuentaRepository = cuentaRepository;
        this.operacionesRepository = operacionesRepository;
    }

    public Cuenta depositar(String iban, double cantidad) {

        if (cantidad <= 0) return null;

        try (Connection conn = DatabaseConnectionManager.getConnection()) {

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

    public Cuenta retirar(String iban, double cantidad) {

        if (cantidad <= 0) return null;

        try (Connection conn = DatabaseConnectionManager.getConnection()) {

            Cuenta cuenta = cuentaRepository.buscarPorIban(conn, iban);

            if (cuenta == null || cuenta.getSaldo() < cantidad)
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

    public boolean transferir(String ibanOrigen,
                              String ibanDestino,
                              double cantidad) {

        if (cantidad <= 0) return false;

        try (Connection conn = DatabaseConnectionManager.getConnection()) {

            conn.setAutoCommit(false);

            try {

                Cuenta origen = cuentaRepository.buscarPorIban(conn, ibanOrigen);
                Cuenta destino = cuentaRepository.buscarPorIban(conn, ibanDestino);

                if (origen == null || destino == null ||
                        origen.getSaldo() < cantidad) {

                    conn.rollback();
                    return false;
                }

                origen.retirar(cantidad);
                destino.ingresar(cantidad);

                cuentaRepository.actualizarSaldo(conn, origen);
                cuentaRepository.actualizarSaldo(conn, destino);

                operacionesRepository.guardar(conn,
                        Math.toIntExact(origen.getId()),
                        TipoOperacion.TRANSFERENCIA_SALIENTE,
                        cantidad);

                operacionesRepository.guardar(conn,
                        Math.toIntExact(destino.getId()),
                        TipoOperacion.TRANSFERENCIA_ENTRANTE,
                        cantidad);

                conn.commit();
                return true;

            } catch (Exception e) {
                conn.rollback();
                throw e;
            }

        } catch (Exception e) {
            throw new RuntimeException("Error en transferencia", e);
        }
    }
}
