package org.javinttdata.cuenta.model;

import org.javinttdata.cliente.model.Cliente;

import java.time.LocalDateTime;

/**
 * Enticad Cuenta
 */
public class Cuenta {

    //Propiedades
    private Long id;
    private String iban;
    private Long cliente_id;
    private double saldo;
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
        this.saldo = 0;
        this.fechaCreacion = LocalDateTime.now();
    }

    public Cuenta() {

    }

    public void setIban(String iban) {
        this.iban = iban;
    }


    public void ingresar(double cantidad) {
        this.saldo += cantidad;
    }

    public void retirar(double cantidad) {
        this.saldo -= cantidad;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
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
