package org.javinttdata.Cuenta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CuentaRepository {
    private final Map<String, Cuenta> cuentasPorIban = new HashMap<>();

    public List<Cuenta> obtenerTodas() {
        return new ArrayList<>(cuentasPorIban.values());
    }
}
