package org.javinttdata.operaciones.repository;

import org.javinttdata.cuenta.model.Cuenta;
import org.javinttdata.operaciones.model.Operacion;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Repositorio de las operaciones
 */
public class OperacionesRepository {
    public Map<String, List<Operacion>> operaciones = new HashMap<>();//Mapa encargado de guardar las opereaciones de cada numero de cuenta
}
