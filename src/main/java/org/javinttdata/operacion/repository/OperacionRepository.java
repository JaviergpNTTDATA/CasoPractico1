package org.javinttdata.operacion.repository;

import org.javinttdata.operacion.model.Operacion;

import java.sql.Connection;
import java.time.LocalDateTime;
import java.util.List;

public interface OperacionRepository {
    Operacion guardar(Operacion movimiento);
    List<Operacion> buscarPorCuentaId(Long cuentaId);
    List<Operacion> buscarPorCuentaIdYFechas(Long cuentaId, LocalDateTime inicio, LocalDateTime fin);
    // Variante transaccional: recibe la Connection activa
    Operacion guardar(Operacion movimiento, Connection conn);
} 