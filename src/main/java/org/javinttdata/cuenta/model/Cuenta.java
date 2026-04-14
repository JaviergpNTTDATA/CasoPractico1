package org.javinttdata.cuenta.model;

import org.javinttdata.cliente.model.Cliente;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Enticad Cuenta
 */
public class Cuenta {

    //Propiedades
    private Long id;
    private String iban;
    private Long cliente_id;
    private BigDecimal saldo;
    private LocalDateTime fechaCreacion;

    //private static int contadorCuentas = 1; // contador global

    public Long getCliente_id() {
        return cliente_id;
    }

    public void setCliente_id(Long cliente_id) {
        this.cliente_id = cliente_id;
    }

    /**
     * Constructor
     *
     * @param titular cliente al que se asigna la cuenta
     */
    public Cuenta(Long titular) {
        this.cliente_id = titular;
        this.saldo = BigDecimal.valueOf(0);
        this.fechaCreacion = LocalDateTime.now();
    }

    public Cuenta() {

    }

    public void setIban(String iban) {
        this.iban = iban;
    }


    public void ingresar(BigDecimal cantidad) {
        if (cantidad == null || cantidad.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Cantidad inválida");
        }
        this.saldo = this.saldo.add(cantidad);
    }

    public void retirar(BigDecimal cantidad) {
        if (cantidad == null || cantidad.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Cantidad inválida");
        }

        if (this.saldo.compareTo(cantidad) < 0) {
            throw new IllegalArgumentException("Saldo insuficiente");
        }

        this.saldo = this.saldo.subtract(cantidad);
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }

    public String getIban() {
        return iban;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }


}
