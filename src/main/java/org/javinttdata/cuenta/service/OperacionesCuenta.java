package org.javinttdata.cuenta.service;

import org.javinttdata.cliente.model.Cliente;
import org.javinttdata.cliente.repository.ClientesRepository;
import org.javinttdata.cuenta.model.Cuenta;
import org.javinttdata.cuenta.repository.CuentaRepository;
import org.javinttdata.common.Globales;

import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Clase encargada de gestionas las acciones del menu de operaciones
 */
public class OperacionesCuenta {

    /**
     * Metodo que se encarga de crear una cuenta
     * @param repository repositorio de cuentas
     * @param repoCli repositorio de clientes
     */
    static void CrearCuenta(CuentaRepository repository, ClientesRepository repoCli) {

        Globales.LimpiarConsola();
        System.out.print("Introduce el ID del cliente: ");
        int id;
        try {
            id = Integer.parseInt(Globales.sc.nextLine());
        } catch (NumberFormatException e) {
            id = -1; //Valor por defecto si falla
        }

        Cliente cliente = repoCli.buscarPorId(id);

        if (cliente == null) {
            System.out.println("El cliente no existe.");
            Globales.Continuar();
            return;
        }

        Cuenta nuevaCuenta = new Cuenta(cliente);
        repository.guardar(nuevaCuenta);

        System.out.println("Cuenta creada correctamente.");
        System.out.println("Numero de cuenta: " + nuevaCuenta.getIban());
        System.out.println("Titular: " + nuevaCuenta.getTitular().getNombre() + " " + nuevaCuenta.getTitular().getApellidos() + " (ID: "+ nuevaCuenta.getTitular().getId() +")");
        System.out.println("Saldo inicial: " + nuevaCuenta.getSaldo());
        Globales.Continuar();
    }

    /**
     * Metodo que se encarga de listar todas las cuentas de un id en especifico
     * @param repository repositorio de cuentas
     * @param repositoryCl repositorio de clientes
     */
    static void ListarCuentas(CuentaRepository repository, ClientesRepository repositoryCl)
    {
        Globales.LimpiarConsola();
        System.out.print("Introduzca el ID del cliente: ");
        int id;
        try {
            id = Integer.parseInt(Globales.sc.nextLine());
        } catch (NumberFormatException e) {
            id = -1; //Valor por defecto si falla
        }

        Cliente encontrado = repositoryCl.buscarPorId(id);
        if(encontrado!=null)
        {
            System.out.println("Cuentas del cliente " + encontrado.getNombre() + encontrado.getApellidos()+":");
            List<Cuenta> cuentasCliente = repository.buscarPorIdCliente(encontrado.getId());

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

        }
        else
        {
            System.out.println("ERROR: No hay ningun cliente con ese id");
        }
        Globales.Continuar();
    }

    /**
     * Metodo encargado de mostrar la informacion dado un numero de cuenta
     * @param repository repositorio de cuentas
     */
    static void InfoCuenta(CuentaRepository repository)
    {
        Globales.LimpiarConsola();
        System.out.print("Introduzca número de cuenta: ");
        String iban = Globales.sc.nextLine();

        Cuenta obtenida = repository.buscarPorIban(iban);

        if(obtenida!= null)
        {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

            System.out.println("Cuenta creada correctamente:");
            System.out.printf("%-20s %s%n", "Número de cuenta:", obtenida.getIban());
            System.out.printf("%-20s %s%n", "Titular:", obtenida.getTitular().getNombre() + " " + obtenida.getTitular().getApellidos());
            System.out.printf("%-20s %,.2f €%n", "Saldo:", obtenida.getSaldo());
            System.out.printf("%-20s %s%n", "Fecha de creación:", obtenida.getFechaCreacion().format(formatter));

        }
        else
        {
            System.out.println("ERROR: No existe una cuenta con ese iban");
        }
        Globales.Continuar();

    }
}
