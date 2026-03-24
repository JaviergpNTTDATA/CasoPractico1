package org.javinttdata.cuenta.model;

import org.javinttdata.cliente.model.Cliente;

import java.time.LocalDateTime;

/**
 * Enticad Cuenta
 */
public class Cuenta {

    //Propiedades
    private String iban;
    private Cliente titular;
    private double saldo;
    private LocalDateTime fechaCreacion;

    private static int contadorCuentas = 1; // contador global


    /**
     * Constructor
     * @param titular cliente al que se asigna la cuenta
     */
    public Cuenta(Cliente titular) {
        this.iban = generarIBAN();
        this.titular = titular;
        this.saldo = 0;
        this.fechaCreacion = LocalDateTime.now();
    }

    /**
     * Metodo que genera un iban con un patron
     * @return iban ya creado
     */
    public static String generarIBAN() {
        String secuencial = String.format("%012d", contadorCuentas);
        contadorCuentas++;
        return "ES91210000" + secuencial;
    }

    public Cliente getTitular() {
        return titular;
    }

    public void setTitular(Cliente titular) {
        this.titular = titular;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo += saldo;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }


}
