package org.javinttdata.cuenta.repository;

import org.javinttdata.cuenta.model.Cuenta;

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.List;
import java.util.Optional;

//Interfaz que tendremos que cumplir a la hora de declarar nuestro repositorio de cuenta
public interface CuentaRepository {
    Cuenta guardar(Cuenta cuenta);
    Optional<Cuenta> buscarPorId(Long id);
    Optional<Cuenta> buscarPorNumero(String numeroCuenta);
    List<Cuenta> buscarPorClienteId(Long clienteId);
    Cuenta actualizarSaldo(Long cuentaId, BigDecimal nuevoSaldo);

    // Variantes transaccionales: reciben la Connection activa del servicio 
    Optional<Cuenta> buscarPorNumero(String numeroCuenta, Connection conn);
    Cuenta actualizarSaldo(Long cuentaId, BigDecimal nuevoSaldo, Connection conn);
} 