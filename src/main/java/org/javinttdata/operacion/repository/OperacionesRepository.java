package org.javinttdata.operacion.repository;

import org.javinttdata.operacion.model.Operacion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Repositorio de las operaciones
 */
public class OperacionesRepository {
    public Map<String, List<Operacion>> operaciones = new HashMap<>();//Mapa encargado de guardar las opereaciones de cada numero de cuenta

}
