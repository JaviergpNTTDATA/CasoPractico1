package org.javinttdata.cuenta.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

//Clase builder que sirve para tener el codigo y la creacion de cuentas mas legible
public class CuentaBuilder {

    private Long id;
    private String iban;
    private Long cliente_id;
    private BigDecimal saldo = BigDecimal.ZERO;
    private LocalDateTime fechaCreacion = LocalDateTime.now();

    public CuentaBuilder id(Long id) {
        this.id = id;
        return this;
    }

    public CuentaBuilder iban(String iban) {
        this.iban = iban;
        return this;
    }

    public CuentaBuilder clienteId(Long cliente_id) {
        this.cliente_id = cliente_id;
        return this;
    }

    public CuentaBuilder saldo(BigDecimal saldo) {
        this.saldo = saldo;
        return this;
    }

    public CuentaBuilder fechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
        return this;
    }

    public Cuenta build() {

        Cuenta cuenta = new Cuenta();

        cuenta.setId(this.id);
        cuenta.setIban(this.iban);
        cuenta.setCliente_id(this.cliente_id);
        cuenta.setSaldo(this.saldo);
        cuenta.setFechaCreacion(this.fechaCreacion);

        return cuenta;
    }
}
