package org.javinttdata.operacion.factory;

import org.javinttdata.operacion.model.Operacion;
import org.javinttdata.operacion.model.enums.TipoOperacion;

import java.math.BigDecimal;

//Clase factory que nos ayuda a tener mas legible los tipos de movimientos
public class MovimientoFactory {

    private MovimientoFactory() {
    }

    public static Operacion crearDeposito(BigDecimal cantidad) {
        return new Operacion(TipoOperacion.DEPOSITO, cantidad);
    }

    public static Operacion crearRetiro(BigDecimal cantidad) {
        return new Operacion(TipoOperacion.RETIRO, cantidad);
    }

    public static Operacion crearTransferenciaSaliente(BigDecimal cantidad) {
        return new Operacion(TipoOperacion.TRANSFERENCIA_SALIENTE, cantidad);
    }

    public static Operacion crearTransferenciaEntrante(BigDecimal cantidad) {
        return new Operacion(TipoOperacion.TRANSFERENCIA_ENTRANTE, cantidad);
    }
}
