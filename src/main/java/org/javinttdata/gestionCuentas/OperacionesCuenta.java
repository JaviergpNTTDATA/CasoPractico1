package org.javinttdata.gestionCuentas;

import org.javinttdata.Cuenta.Cuenta;
import org.javinttdata.Cuenta.CuentaRepository;
import org.javinttdata.Globales;

public class OperacionesCuenta {

    static void CrearCuenta(CuentaRepository repository) {

        System.out.print("Introduce el ID del cliente: ");
        String id = Globales.sc.nextLine();

        Cliente cliente = buscarClientePorId(id);

        if (cliente == null) {
            System.out.println("El cliente no existe.");
            Globales.Continuar();
            return;
        }

        Cuenta nuevaCuenta = new Cuenta(cliente);
        cuentas.add(nuevaCuenta);

        System.out.println("Cuenta creada correctamente.");
        System.out.println("Numero de cuenta: " + nuevaCuenta.getIban());
        System.out.println("Titular: " + nuevaCuenta.getTitular());
        System.out.println("Saldo inicial: " + nuevaCuenta.getSaldo());
        Globales.Continuar();
    }
}
