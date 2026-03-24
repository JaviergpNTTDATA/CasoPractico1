package org.javinttdata.operaciones.model;

import org.javinttdata.operaciones.model.enums.TipoOperacion;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

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

    private TipoOperacion tipoO;
    private double cantidad;
    private LocalDateTime fecha;

    public Operacion(TipoOperacion tipoO, double cantidad) {
        this.tipoO = tipoO;
        this.cantidad = cantidad;
        this.fecha = LocalDateTime.now();
    }
}
