package org.javinttdata.view;

import org.javinttdata.cliente.repository.ClientesRepository;
import org.javinttdata.consulta.service.GestionConsultas;
import org.javinttdata.cuenta.repository.CuentaRepository;
import org.javinttdata.common.Globales;
import org.javinttdata.cliente.service.GestionClientes;
import org.javinttdata.cuenta.service.GestionCuentas;
import org.javinttdata.operacion.repository.OperacionesRepository;
import org.javinttdata.operacion.service.GestionOperaciones;

/**
 * Clase que se encarga de mostrar el menu principal
 */
public class MenuPrincipal {

    //Declaramos los repositorios que vamos a usar
    private final CuentaRepository repositoryCu;
    private final ClientesRepository repositoryCl;
    private final OperacionesRepository repositoryOp;

    /**
     * Asignamos los repositorios en el constructor
     * @param repositoryCu repositorio de cuentas
     * @param repositoryCl repositorio de clientes
     * @param repositoryOp repositorio de operaciones
     */
    public MenuPrincipal(CuentaRepository repositoryCu, ClientesRepository repositoryCl, OperacionesRepository repositoryOp) {
        this.repositoryCu = repositoryCu;
        this.repositoryCl = repositoryCl;
        this.repositoryOp = repositoryOp;
    }

    /**
     * Metodo que se encarga de mostrar el menu
     */
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

    /**
     * Metodo que gestiona la seleccion de opciones del menu
     */
    public void OpcionesMenuP()
    {
        int opcion = 0;

        GestionCuentas gestionCu = new GestionCuentas(repositoryCu, repositoryCl);
        GestionClientes gestionCl = new GestionClientes(repositoryCl);
        GestionOperaciones gestionOp = new GestionOperaciones(repositoryOp, repositoryCu);
        GestionConsultas gestionCo = new GestionConsultas(repositoryOp,repositoryCu);

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
                    gestionCl.OpcionesMenuC();
                    break;
                case 2:
                    gestionCu.OpcionesMenuC();
                    break;
                case 3:
                    gestionOp.OpcionesMenuC();
                    break;
                case 4:
                    gestionCo.OpcionesMenuC();
                    break;
                case 5:
                    System.out.println("Saliendo del sistema...");
                    break;
                default:
                    System.out.println("Opción no válida. Intente nuevamente.");
                    Globales.Continuar();
            }

            System.out.println();

        } while (opcion != 5);
    }
}
