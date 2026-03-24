package org.javinttdata.cuenta.repository;

import org.javinttdata.cuenta.model.Cuenta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Repositorio de Cuenta
 */
public class CuentaRepository {
    private final Map<String, Cuenta> cuentasPorIban = new HashMap<>();

    /**
     * Metodo que devuelve todas las cuentas
     * @return lista de todas las cuentas
     */
    public List<Cuenta> obtenerTodas() {
        return new ArrayList<>(cuentasPorIban.values());
    }

    /**
     * Metodo que devuelve una lista de cuentas en base al id
     * @param idCliente id del titular de las cuentas a buscar
     * @return lista de cuentas con el id
     */
    public List<Cuenta> buscarPorIdCliente(int idCliente) {

        List<Cuenta> resultado = new ArrayList<>();

        for (Cuenta cuenta : cuentasPorIban.values()) {
            if (cuenta.getTitular().getId() == idCliente) {
                resultado.add(cuenta);
            }
        }

        return resultado;
    }

    public void guardar(Cuenta cuenta) {
        cuentasPorIban.put(cuenta.getIban(), cuenta);
    }

    public Cuenta buscarPorIban(String iban) {
        return cuentasPorIban.get(iban);
    }

}
