package org.javinttdata.gestionCuentas;

import org.javinttdata.Cuenta.CuentaRepository;
import org.javinttdata.Globales;

public class GestionCuentas {

    private final CuentaRepository repository;

    public GestionCuentas(CuentaRepository repository) {
        this.repository = repository;
    }

    private void MenuC() {
        System.out.println("--- GESTIÓN DE CUENTAS ---");
        System.out.println("1. Crear cuenta");
        System.out.println("2. Listar cuentas de clientes");
        System.out.println("3. Ver informacion de la cuenta");
        System.out.println("4. Volver");
        System.out.println();
    }

    public void OpcionesMenuC() {

        int opcion = 0;

        do {
            Globales.LimpiarConsola();
            MenuC();
            System.out.print("Seleccione una opción: ");
            try {
                opcion = Integer.parseInt(Globales.sc.nextLine());
            } catch (NumberFormatException e) {
                opcion = -1;
            }

            switch (opcion) {
                case 1:
                    break;
                case 2:
                    break;
                case 3:
                    break;
                case 4:
                    System.out.println("Volviendo al menú...");
                    Globales.Continuar();
                    break;
                default:
                    System.out.println("Opción no válida.");
                    Globales.Continuar();
            }

        } while (opcion != 4);
    }
}