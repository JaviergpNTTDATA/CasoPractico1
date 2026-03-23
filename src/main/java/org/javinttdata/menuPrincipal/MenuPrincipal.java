package org.javinttdata.menuPrincipal;

import org.javinttdata.Cuenta.CuentaRepository;
import org.javinttdata.Globales;
import org.javinttdata.gestionCuentas.GestionCuentas;

public class MenuPrincipal {

    private final CuentaRepository repositoryCu;

    public MenuPrincipal(CuentaRepository repositoryCu) {
        this.repositoryCu = repositoryCu;
    }

    static void MenuP()
    {
        System.out.println("=========================================");
        System.out.println("        NOVABANK - SISTEMA DE OPERACIONES");
        System.out.println("=========================================");
        System.out.println("1. Gestión de clientes");
        System.out.println("2. Gestión de cuentas");
        System.out.println("3. Operaciones financieras");
        System.out.println("4. Consultas");
        System.out.println("5. Salir");
        System.out.println();
    }
    public void OpcionesMenuP()
    {
        int opcion = 0;

        GestionCuentas gestionCu = new GestionCuentas(repositoryCu);

        do {
            Globales.LimpiarConsola();
            MenuP();
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
                    gestionCu.OpcionesMenuC();
                    break;
                case 3:

                    break;
                case 4:

                    break;
                case 5:
                    System.out.println("Saliendo del sistema...");
                    break;
                default:
                    System.out.println("Opción no válida. Intente nuevamente.");
            }

            System.out.println();

        } while (opcion != 5);
    }
}
