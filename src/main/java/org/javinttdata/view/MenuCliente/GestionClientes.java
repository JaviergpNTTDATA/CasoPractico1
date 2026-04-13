package org.javinttdata.view.MenuCliente;

import org.javinttdata.cliente.model.Cliente;
import org.javinttdata.cliente.service.ClienteService;
import org.javinttdata.common.Globales;

public class GestionClientes {

    private final ClienteService service;

    public GestionClientes(ClienteService service) {
        this.service = service;
    }

    private void menu() {
        System.out.println("--- GESTIÓN DE CLIENTES ---");
        System.out.println("1. Crear cliente");
        System.out.println("2. Buscar cliente");
        System.out.println("3. Listar clientes");
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
                case 1 -> crearCliente();
                case 2 -> buscarCliente();
                case 3 -> mostrarClientes();
                case 4 -> {
                    System.out.println("Volviendo...");
                    Globales.Continuar();
                }
                default -> {
                    System.out.println("Opción no válida.");
                    Globales.Continuar();
                }
            }

        } while (opcion != 4);
    }

    private void crearCliente() {

        Globales.LimpiarConsola();

        System.out.print("Nombre: ");
        String nombre = Globales.sc.nextLine();

        System.out.print("Apellidos: ");
        String apellidos = Globales.sc.nextLine();

        System.out.print("DNI/NIF: ");
        String dni = Globales.sc.nextLine();

        System.out.print("Email: ");
        String email = Globales.sc.nextLine();

        System.out.print("Teléfono: ");
        String telefono = Globales.sc.nextLine();

        try {
            Cliente cliente = service.crearCliente(nombre, apellidos, dni, email, telefono);

            System.out.println("ID desde objeto: " + cliente.getId());
            System.out.println("\nCliente creado correctamente.\nID cliente: " + cliente.getId() + "\nVolviendo al menu");

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        Globales.Continuar();
    }

    private void buscarCliente() {

        Globales.LimpiarConsola();

        Cliente encontrado;

        System.out.print("Busqueda de cliente(pulse 1 para buscar por id || 2 si es por dni): ");

        int opcion;

        try {
            opcion = Integer.parseInt(Globales.sc.nextLine());
        } catch (NumberFormatException e) {
            opcion = -1;
        }

        switch (opcion) {

            case 1 -> {
                System.out.print("\nHas elegido buscar por id.\nIntroduzca el id a buscar: ");

                int id;

                try {
                    id = Integer.parseInt(Globales.sc.nextLine());
                } catch (NumberFormatException e) {
                    id = -1;
                }

                encontrado = service.buscarPorId(id);

                if (encontrado != null) {
                    System.out.println("Cliente encontrado:");
                    System.out.println("ID:        " + encontrado.getId());
                    System.out.println("Nombre:    " + encontrado.getNombre() + " " + encontrado.getApellidos());
                    System.out.println("DNI:       " + encontrado.getDni());
                    System.out.println("Email:     " + encontrado.getEmail());
                    System.out.println("Teléfono:  " + encontrado.getTelefono());
                } else {
                    System.out.println("ERROR: No se encontró ningún cliente con ID " + id);
                }

                Globales.Continuar();
            }

            case 2 -> {
                System.out.print("\nHas elegido buscar por dni.\nIntroduzca el id a buscar: ");

                String dni = Globales.sc.nextLine();

                encontrado = service.buscarPorDni(dni);

                if (encontrado != null) {
                    System.out.println("Cliente encontrado:");
                    System.out.println("ID:        " + encontrado.getId());
                    System.out.println("Nombre:    " + encontrado.getNombre() + " " + encontrado.getApellidos());
                    System.out.println("DNI:       " + encontrado.getDni());
                    System.out.println("Email:     " + encontrado.getEmail());
                    System.out.println("Teléfono:  " + encontrado.getTelefono());
                } else {
                    System.out.println("ERROR: No se encontró ningún cliente con DNI " + dni);
                }

                Globales.Continuar();
            }

            default -> {
                System.out.println("No has elegido una opcion valida.\nVolviendo al menu");
                Globales.Continuar();
            }
        }
    }

    private void mostrarClientes() {

        Globales.LimpiarConsola();

        if (!service.obtenerTodos().isEmpty()) {

            System.out.printf("%-5s | %-20s | %-12s | %-25s | %-10s%n",
                    "ID", "Nombre", "DNI", "Email", "Teléfono");

            System.out.println("------|----------------------|--------------|---------------------------|------------");

            for (Cliente c : service.obtenerTodos()) {

                String nombreCompleto = c.getNombre() + " " + c.getApellidos();

                System.out.printf("%-5d | %-20s | %-12s | %-25s | %-10s%n",
                        c.getId(),
                        nombreCompleto,
                        c.getDni(),
                        c.getEmail(),
                        c.getTelefono());
            }

        } else {
            System.out.println("No hay ningun cliente registrado");
        }

        Globales.Continuar();
    }
}
