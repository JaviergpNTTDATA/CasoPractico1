package org.javinttdata.cuenta.repository;

import org.javinttdata.cuenta.model.Cuenta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CuentaRepository {
    public final Map<String, Cuenta> cuentasPorIban = new HashMap<>();

    public List<Cuenta> obtenerTodas() {
        return new ArrayList<>(cuentasPorIban.values());
    }
    public List<Cuenta> buscarPorIdCliente(int idCliente) {

        List<Cuenta> resultado = new ArrayList<>();

        for (Cuenta cuenta : cuentasPorIban.values()) {
            if (cuenta.getTitular().getId() == idCliente) {
                resultado.add(cuenta);
            }
        }

        return resultado;
    }

}
