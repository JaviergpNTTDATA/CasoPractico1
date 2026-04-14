package org.javinttdata.view.MenuOperacion;

import org.javinttdata.common.Globales;
import org.javinttdata.cuenta.model.Cuenta;
import org.javinttdata.operacion.service.OperacionService;

public class GestionOperaciones {

    private final OperacionService service;

    public GestionOperaciones(OperacionService service) {
        this.service = service;
    }

    private void menu() {
        System.out.println("--- OPERACIONES FINANCIERAS ---");
        System.out.println("1. Depositar dinero");
        System.out.println("2. Retirar dinero");
        System.out.println("3. Transferencia entre cuentas");
        System.out.println("4. Volver");
        System.out.println();
    }

    public void opcionesMenu() {

        int opcion;

        do {
            Globales.LimpiarConsola();
            menu();
            System.out.print("Seleccione una opción: ");

            try {
                opcion = Integer.parseInt(Globales.sc.nextLine());
            } catch (NumberFormatException e) {
                opcion = -1;
            }

            switch (opcion) {
                case 1 -> deposito();
                case 2 -> retirar();
                case 3 -> transferencia();
                case 4 -> {
                    System.out.println("Volviendo al menú...");
                    Globales.Continuar();
                }
                default -> {
                    System.out.println("Opción no válida.");
                    Globales.Continuar();
                }
            }

        } while (opcion != 4);
    }

    private void deposito() {

        Globales.LimpiarConsola();
        System.out.print("Indica la cantidad a depositar: ");

        double cantidad;
        try {
            cantidad = Double.parseDouble(Globales.sc.nextLine());
        } catch (NumberFormatException e) {
            cantidad = -1;
        }

        System.out.print("Indica el numero de cuenta: ");
        String nCuenta = Globales.sc.nextLine();

        Cuenta cuenta = service.depositar(nCuenta, cantidad);

        if (cantidad <= 0) {
            System.out.println("La cantidad debe de ser mayor a 0");
        } else if (cuenta == null) {
            System.out.println("La cuenta no existe en la base de datos");
        } else {
            System.out.println("Depósito realizado correctamente.");
            System.out.println("Cuenta:      " + nCuenta);
            System.out.println("Importe:     +" + cantidad + "€");
            System.out.printf("Nuevo saldo: %,.2f €%n", cuenta.getSaldo());
        }

        Globales.Continuar();
    }

    private void retirar() {

        Globales.LimpiarConsola();
        System.out.print("Indica la cantidad a retirar: ");

        double cantidad;
        try {
            cantidad = Double.parseDouble(Globales.sc.nextLine());
        } catch (NumberFormatException e) {
            cantidad = -1;
        }

        System.out.print("Indica el numero de cuenta: ");
        String nCuenta = Globales.sc.nextLine();

        Cuenta cuenta = service.retirar(nCuenta, cantidad);

        if (cantidad <= 0) {
            System.out.println("La cantidad debe de ser mayor a 0");
        } else if (cuenta == null) {
            System.out.println("La cuenta no existe en la base de datos o saldo insuficiente");
        } else {
            System.out.println("Retiro realizado correctamente.");
            System.out.println("Cuenta:      " + nCuenta);
            System.out.println("Importe:     -" + cantidad + "€");
            System.out.printf("Nuevo saldo: %,.2f €%n", cuenta.getSaldo());
        }

        Globales.Continuar();
    }

    private void transferencia() {

        Globales.LimpiarConsola();
        System.out.print("Indica la cantidad a transferir: ");

        double cantidad;
        try {
            cantidad = Double.parseDouble(Globales.sc.nextLine());
        } catch (NumberFormatException e) {
            cantidad = -1;
        }

        System.out.print("Indica el numero de cuenta origen: ");
        String nCuentaO = Globales.sc.nextLine();

        System.out.print("Indica el numero de cuenta destino: ");
        String nCuentaD = Globales.sc.nextLine();

        boolean ok = service.transferir(nCuentaO, nCuentaD, cantidad);

        if (cantidad <= 0) {
            System.out.println("La cantidad debe de ser mayor a 0");
        } else if (!ok) {
            System.out.println("ERROR: Transferencia no realizada (cuentas inexistentes o saldo insuficiente)");
        } else {
            System.out.println("Transferencia realizada correctamente.");
            System.out.println("Cuenta origen: " + nCuentaO + " ->  -" + cantidad + "€");
            System.out.println("Cuenta destino: " + nCuentaD + " ->  +" + cantidad + "€");
        }

        Globales.Continuar();
    }
}
