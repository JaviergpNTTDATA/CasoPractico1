package org.javinttdata.view.MenuConsultas;

import org.javinttdata.common.Globales;
import org.javinttdata.consulta.service.ConsultaService;
import org.javinttdata.cuenta.model.Cuenta;
import org.javinttdata.operacion.model.Operacion;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class GestionConsultas {

    private final ConsultaService service;

    private static final DateTimeFormatter formato =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public GestionConsultas(ConsultaService service) {
        this.service = service;
    }

    private void menu() {
        System.out.println("--- CONSULTAS ---");
        System.out.println("1. Consultar saldo");
        System.out.println("2. Historial de movimientos");
        System.out.println("3. Movimientos por rango de fecha");
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
                case 1 -> consultarSaldo();
                case 2 -> historial();
                case 3 -> movimientosPorFecha();
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

    private void consultarSaldo() {
        Globales.LimpiarConsola();

        System.out.print("Introduzca número de cuenta: ");
        String iban = Globales.sc.nextLine();

        Cuenta cuenta = service.consultarSaldo(iban);

        if (cuenta != null) {
            System.out.printf("Saldo actual: %,.2f €%n",
                    cuenta.getSaldo());
        } else {
            System.out.println("La cuenta no existe.");
        }

        Globales.Continuar();
    }

    private void historial() {

        Globales.LimpiarConsola();
        System.out.print("Introduzca número de cuenta: ");
        String iban = Globales.sc.nextLine();

        List<Operacion> lista =
                service.historialMovimientos(iban);

        if (lista == null) {
            System.out.println("La cuenta no existe.");
        } else if (lista.isEmpty()) {
            System.out.println("No hay movimientos.");
        } else {

            System.out.printf("%-20s | %-25s | %-12s%n",
                    "Fecha", "Tipo", "Cantidad");
            System.out.println("---------------------|--------------------------|------------");

            for (Operacion op : lista) {
                System.out.printf("%-20s | %-25s | %-12.2f €%n",
                        op.getFecha().format(formato),
                        op.getTipoO().name(),
                        op.getCantidad());
            }
        }

        Globales.Continuar();
    }

    private void movimientosPorFecha() {

        Globales.LimpiarConsola();
        try {
            System.out.print("Número de cuenta: ");
            String iban = Globales.sc.nextLine();

            System.out.print("Fecha inicio (yyyy-MM-dd): ");
            LocalDate inicio =
                    LocalDate.parse(Globales.sc.nextLine());

            System.out.print("Fecha fin (yyyy-MM-dd): ");
            LocalDate fin =
                    LocalDate.parse(Globales.sc.nextLine());

            List<Operacion> lista =
                    service.movimientosPorFecha(iban,
                            inicio, fin);

            if (lista == null) {
                System.out.println("La cuenta no existe.");
            } else if (lista.isEmpty()) {
                System.out.println("No hay movimientos en ese rango.");
            } else {

                System.out.printf("%-20s | %-25s | %-12s%n",
                        "Fecha", "Tipo", "Cantidad");
                System.out.println("---------------------|--------------------------|------------");

                for (Operacion op : lista) {
                    System.out.printf("%-20s | %-25s | %-12.2f €%n",
                            op.getFecha().format(formato),
                            op.getTipoO().name(),
                            op.getCantidad());
                }
            }

        } catch (Exception e) {
            System.out.println("Formato de fecha incorrecto.");
        }

        Globales.Continuar();
    }
}
