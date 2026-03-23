package org.javinttdata.menuPrincipal;

import org.javinttdata.Cliente.ClientesRepository;
import org.javinttdata.Globales;
import org.javinttdata.gestionClientes.GestionClientes;

public class MenuPrincipal {

    private final ClientesRepository repository;

    public MenuPrincipal(ClientesRepository repository) {
        this.repository = repository;
    }

    public void MenuP() {
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

    public void OpcionesMenuP() {

        int opcion;
        GestionClientes gestion = new GestionClientes(repository);

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

                    gestion.opcionesMenuC();
                    break;
                case 5:
                    System.out.println("Saliendo del sistema...");
                    break;
                default:
                    System.out.println("Opción no válida.");
                    Globales.Continuar();
            }

        } while (opcion != 5);
    }
}
