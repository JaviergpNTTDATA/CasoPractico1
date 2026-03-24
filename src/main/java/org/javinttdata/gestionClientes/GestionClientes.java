package org.javinttdata.gestionClientes;

import org.javinttdata.Cliente.ClientesRepository;
import org.javinttdata.Globales;

public class GestionClientes {

    private final ClientesRepository repository;

    public GestionClientes(ClientesRepository repository) {
        this.repository = repository;
    }

    private void menuC() {
        System.out.println("--- GESTIÓN DE CLIENTES ---");
        System.out.println("1. Crear cliente");
        System.out.println("2. Buscar cliente");
        System.out.println("3. Listar cliente");
        System.out.println("4. Volver");
        System.out.println();
    }

    public void opcionesMenuC() {

        int opcion;

        do {
            Globales.LimpiarConsola();
            menuC();
            System.out.print("Seleccione una opción: ");

            try {
                opcion = Integer.parseInt(Globales.sc.nextLine());
            } catch (NumberFormatException e) {
                opcion = -1;
            }

            switch (opcion) {
                case 1:
                    OperacionesCliente.CrearCliente(repository);
                    break;
                case 2:
                    OperacionesCliente.BuscarCliente(repository);
                    break;
                case 3:
                    OperacionesCliente.MostrarClientes(repository);
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
