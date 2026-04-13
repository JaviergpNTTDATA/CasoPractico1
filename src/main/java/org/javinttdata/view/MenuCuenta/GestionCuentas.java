package org.javinttdata.view.MenuCuenta;

import org.javinttdata.cuenta.model.Cuenta;
import org.javinttdata.cuenta.service.CuentaService;
import org.javinttdata.cliente.model.Cliente;
import org.javinttdata.common.Globales;

import java.time.format.DateTimeFormatter;
import java.util.List;

public class GestionCuentas {

    private final CuentaService service;

    public GestionCuentas(CuentaService service) {
        this.service = service;
    }

    private void menu() {
        System.out.println("--- GESTIÓN DE CUENTAS ---");
        System.out.println("1. Crear cuenta");
        System.out.println("2. Listar cuentas de clientes");
        System.out.println("3. Ver informacion de la cuenta");
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
                case 1 -> crearCuenta();
                case 2 -> listarCuentas();
                case 3 -> infoCuenta();
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

    private void crearCuenta() {

        Globales.LimpiarConsola();
        System.out.print("Introduce el ID del cliente: ");

        long id;

        try {
            id = Long.parseLong(Globales.sc.nextLine());
        } catch (NumberFormatException e) {
            id = -1;
        }

        Cuenta nuevaCuenta = service.crearCuenta(id);

        if (nuevaCuenta == null) {
            System.out.println("El cliente no existe.");
        } else {
            System.out.println("Cuenta creada correctamente.");
            System.out.println("Numero de cuenta: " + nuevaCuenta.getIban());
            //System.out.println("Titular: " + nuevaCuenta.getTitular().getNombre() + " " + nuevaCuenta.getTitular().getApellidos() + " (ID: " + nuevaCuenta.getTitular().getId() + ")");
            System.out.println("Saldo inicial: " + nuevaCuenta.getSaldo());
        }

        Globales.Continuar();
    }

    private void listarCuentas() {

        Globales.LimpiarConsola();
        System.out.print("Introduzca el ID del cliente: ");

        long id;

        try {
            id = Long.parseLong(Globales.sc.nextLine());
        } catch (NumberFormatException e) {
            id = -1;
        }

        Cliente encontrado = service.buscarCliente(id);

        if (encontrado != null) {

            System.out.println("Cuentas del cliente " +
                    encontrado.getNombre() + " " +
                    encontrado.getApellidos() + ":");

            List<Cuenta> cuentasCliente = service.listarCuentasCliente(id);

            if (!cuentasCliente.isEmpty()) {

                System.out.printf("%-25s | %-12s%n", "Número de cuenta", "Saldo");
                System.out.println("--------------------------|--------------");

                for (Cuenta c : cuentasCliente) {
                    System.out.printf("%-25s | %,.2f €%n",
                            c.getIban(),
                            c.getSaldo());
                }

            } else {
                System.out.println("El cliente no tiene cuentas asociadas.");
            }

        } else {
            System.out.println("ERROR: No hay ningun cliente con ese id");
        }

        Globales.Continuar();
    }

    private void infoCuenta() {

        Globales.LimpiarConsola();
        System.out.print("Introduzca número de cuenta: ");

        String iban = Globales.sc.nextLine();

        Cuenta obtenida = service.buscarPorIban(iban);

        if (obtenida != null) {

            DateTimeFormatter formatter =
                    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

            System.out.println("Cuenta creada correctamente:");
            System.out.printf("%-20s %s%n", "Número de cuenta:", obtenida.getIban());
            //System.out.printf("%-20s %s%n", "Titular:", obtenida.getTitular().getNombre() + " " + obtenida.getTitular().getApellidos());
            System.out.printf("%-20s %,.2f €%n", "Saldo:", obtenida.getSaldo());
            System.out.printf("%-20s %s%n", "Fecha de creación:",
                    obtenida.getFechaCreacion().format(formatter));

        } else {
            System.out.println("ERROR: No existe una cuenta con ese iban");
        }

        Globales.Continuar();
    }
}
