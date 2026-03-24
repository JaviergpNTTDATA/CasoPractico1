package org.javinttdata.cliente.service;

import org.javinttdata.cliente.model.Cliente;
import org.javinttdata.cliente.repository.ClientesRepository;
import org.javinttdata.common.Globales;

/**
 * Clase que proprciona las acciones del menu
 */
public class OperacionesCliente {

    /**
     * Metodo que se encarga de la creacion de un cliente
     * @param repository repositorio de clientes
     */
    public static void CrearCliente(ClientesRepository repository) {

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
            Cliente cliente = new Cliente(nombre, apellidos, dni, email, telefono);
            repository.guardar(cliente);

            System.out.println("\nCliente creado correctamente.\nID cliente: " + cliente.getId() + "\nVolviendo al menu");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }


        Globales.Continuar();
    }

    /**
     * Metodo que se encarga de buscar clientes, ya sea por id o por dni
     * @param repository repositorio de clientes
     */
    public static void BuscarCliente(ClientesRepository repository) {
        Globales.LimpiarConsola();
        Cliente encontrado;
        System.out.print("Busqueda de cliente(pulse 1 para buscar por id || 2 si es por dni): ");
        int opcion;
        try {
            opcion = Integer.parseInt(Globales.sc.nextLine());
        } catch (NumberFormatException e) {
            opcion = -1; //Valor por defecto si falla
        }
        switch (opcion) {
            case 1:
                System.out.print("\nHas elegido buscar por id.\nIntroduzca el id a buscar: ");
                int id;
                try {
                    id = Integer.parseInt(Globales.sc.nextLine());
                } catch (NumberFormatException e) {
                    id = -1; //Valor por defecto si falla
                }
                encontrado = repository.buscarPorId(id);
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
                break;
            case 2:
                System.out.print("\nHas elegido buscar por dni.\nIntroduzca el id a buscar: ");
                String dni;

                dni = Globales.sc.nextLine();

                encontrado = repository.buscarPorDni(dni);
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
                break;
        }
    }

    /**
     * Metodo que muestra los clientes que se han creado
     * @param repository repositorio de clientes
     */
    public static void MostrarClientes(ClientesRepository repository) {
        Globales.LimpiarConsola();


        if (!repository.obtenerTodos().isEmpty()) {
            System.out.printf("%-5s | %-20s | %-12s | %-25s | %-10s%n", "ID", "Nombre", "DNI", "Email", "Teléfono");

            System.out.println("------|----------------------|--------------|---------------------------|------------");
            for (Cliente c : repository.obtenerTodos()) {

                String nombreCompleto = c.getNombre() + " " + c.getApellidos();

                System.out.printf("%-5d | %-20s | %-12s | %-25s | %-10s%n", c.getId(), nombreCompleto, c.getDni(), c.getEmail(), c.getTelefono());
            }
        } else {
            System.out.println("No hay ningun cliente registrado");
        }

        Globales.Continuar();
    }
}
