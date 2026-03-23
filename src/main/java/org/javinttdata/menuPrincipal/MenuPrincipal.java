package org.javinttdata.menuPrincipal;

import org.javinttdata.Globales;

public class MenuPrincipal {
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
    public static void OpcionesMenuP()
    {
        int opcion;

        do {
            MenuP();
            System.out.print("Seleccione una opción: ");
            opcion = Globales.sc.nextInt();

            switch (opcion) {
                case 1:

                    break;
                case 2:

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
