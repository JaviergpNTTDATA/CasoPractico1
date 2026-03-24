package org.javinttdata.operaciones.repository;

import org.javinttdata.cuenta.model.Cuenta;
import org.javinttdata.operaciones.model.Operacion;

import java.util.HashMap;

public class OperacionesRepository {
    public HashMap<Cuenta, Operacion> operaciones = new HashMap<>();
}
