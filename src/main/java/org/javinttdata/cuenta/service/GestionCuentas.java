package org.javinttdata.cuenta.service;

import org.javinttdata.cliente.repository.ClientesRepository;
import org.javinttdata.cuenta.repository.CuentaRepository;
import org.javinttdata.common.Globales;

/**
 * Clase que se encarga de mostrar el menu de gestion
 */
public class GestionCuentas {

    //Declaramos los repositorios que vamos a usar
    private final CuentaRepository repositoryCu;
    private final ClientesRepository repositoryCl;

    /**
     * Constructor donde asignamos los repostorios
     * @param repository repositorio de cuentas
     * @param repositoryCl repostirio de clientes
     */
    public GestionCuentas(CuentaRepository repository, ClientesRepository repositoryCl) {
        this.repositoryCu = repository;
        this.repositoryCl = repositoryCl;
    }

    /**
     * Metodo que muestra el menu
     */
    private void MenuC() {
        System.out.println("--- GESTIÓN DE CUENTAS ---");
        System.out.println("1. Crear cuenta");
        System.out.println("2. Listar cuentas de clientes");
        System.out.println("3. Ver informacion de la cuenta");
        System.out.println("4. Volver");
        System.out.println();
    }

    /**
     * Metodo que se encarga de la seleccion de opciones del menu
     */
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
                    OperacionesCuenta.CrearCuenta(repositoryCu,repositoryCl);
                    break;
                case 2:
                    OperacionesCuenta.ListarCuentas(repositoryCu,repositoryCl);
                    break;
                case 3:
                    OperacionesCuenta.InfoCuenta(repositoryCu);
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