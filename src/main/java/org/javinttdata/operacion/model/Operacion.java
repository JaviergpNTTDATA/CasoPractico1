package org.javinttdata.operacion.model;

import org.javinttdata.operacion.model.enums.TipoOperacion;

import java.time.LocalDateTime;

/**
 * Entidad operacion
 */
public class Operacion {
    public TipoOperacion getTipoO() {
        return tipoO;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public void setTipoO(TipoOperacion tipoO) {
        this.tipoO = tipoO;
    }

    public double getCantidad() {
        return cantidad;
    }

    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }

    //Propiedad
    private TipoOperacion tipoO;
    private double cantidad;
    private LocalDateTime fecha;

    /**
     * Constructor en el que asignamos las propiedades
     * @param tipoO tipo de operacion
     * @param cantidad cantidad de la operacion
     */
    public Operacion(TipoOperacion tipoO, double cantidad) {
        this.tipoO = tipoO;
        this.cantidad = cantidad;
        this.fecha = LocalDateTime.now();
    }
}
