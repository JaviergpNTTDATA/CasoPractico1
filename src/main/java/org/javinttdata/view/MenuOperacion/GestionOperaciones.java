package org.javinttdata.view.MenuOperacion;

import org.javinttdata.common.Globales;
import org.javinttdata.cuenta.model.Cuenta;
import org.javinttdata.operacion.service.OperacionService;

import java.math.BigDecimal;

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

        BigDecimal cantidad;
        try {
            cantidad = new BigDecimal(Globales.sc.nextLine());
        } catch (NumberFormatException e) {
            cantidad = BigDecimal.valueOf(-1);
        }

        System.out.print("Indica el numero de cuenta: ");
        String nCuenta = Globales.sc.nextLine();

        if (cantidad.compareTo(BigDecimal.ZERO) <= 0) {
            System.out.println("La cantidad debe ser mayor a 0");
            Globales.Continuar();
            return;
        }

        Cuenta cuenta = service.depositar(nCuenta, cantidad);

        if (cuenta == null) {
            System.out.println("La cuenta no existe en la base de datos");
        } else {
            System.out.println("Depósito realizado correctamente.");
            System.out.println("Cuenta:      " + nCuenta);
            System.out.println("Importe:     +" + cantidad + "€");
            System.out.println("Nuevo saldo: " + cuenta.getSaldo() + " €");
        }

        Globales.Continuar();
    }

    private void retirar() {

        Globales.LimpiarConsola();
        System.out.print("Indica la cantidad a retirar: ");

        BigDecimal cantidad;
        try {
            cantidad = new BigDecimal(Globales.sc.nextLine());
        } catch (NumberFormatException e) {
            cantidad = BigDecimal.valueOf(-1);
        }

        System.out.print("Indica el numero de cuenta: ");
        String nCuenta = Globales.sc.nextLine();

        if (cantidad.compareTo(BigDecimal.ZERO) <= 0) {
            System.out.println("La cantidad debe ser mayor a 0");
            Globales.Continuar();
            return;
        }

        Cuenta cuenta = service.retirar(nCuenta, cantidad);

        if (cuenta == null) {
            System.out.println("La cuenta no existe o saldo insuficiente");
        } else {
            System.out.println("Retiro realizado correctamente.");
            System.out.println("Cuenta:      " + nCuenta);
            System.out.println("Importe:     -" + cantidad + "€");
            System.out.println("Nuevo saldo: " + cuenta.getSaldo() + " €");
        }

        Globales.Continuar();
    }

    private void transferencia() {

        Globales.LimpiarConsola();
        System.out.print("Indica la cantidad a transferir: ");

        BigDecimal cantidad;
        try {
            cantidad = new BigDecimal(Globales.sc.nextLine());
        } catch (NumberFormatException e) {
            cantidad = BigDecimal.valueOf(-1);
        }

        System.out.print("Indica el numero de cuenta origen: ");
        String nCuentaO = Globales.sc.nextLine();

        System.out.print("Indica el numero de cuenta destino: ");
        String nCuentaD = Globales.sc.nextLine();

        if (cantidad.compareTo(BigDecimal.ZERO) <= 0) {
            System.out.println("La cantidad debe ser mayor a 0");
            Globales.Continuar();
            return;
        }

        try {
            service.transferir(nCuentaO, nCuentaD, cantidad);

            System.out.println("Transferencia realizada correctamente.");
            System.out.println("Cuenta origen:  " + nCuentaO + " -> -" + cantidad + "€");
            System.out.println("Cuenta destino: " + nCuentaD + " -> +" + cantidad + "€");

        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
        }

        Globales.Continuar();
    }
}
