package org.javinttdata.operacion.model;

import org.javinttdata.operacion.model.enums.TipoOperacion;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Entidad operacion
 */
public class Operacion {

    //Propiedad
    private TipoOperacion tipoO;
    private BigDecimal cantidad;
    private LocalDateTime fecha;


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

    public BigDecimal getCantidad() {
        return cantidad;
    }

    public void setCantidad(BigDecimal cantidad) {
        this.cantidad = cantidad;
    }


    /**
     * Constructor en el que asignamos las propiedades
     * @param tipoO tipo de operacion
     * @param cantidad cantidad de la operacion
     */
    public Operacion(TipoOperacion tipoO, BigDecimal cantidad) {
        this.tipoO = tipoO;
        this.cantidad = cantidad;
        this.fecha = LocalDateTime.now();
    }
}
