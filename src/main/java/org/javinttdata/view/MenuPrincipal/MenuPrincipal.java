package org.javinttdata.view.MenuPrincipal;

import org.javinttdata.common.Globales;
import org.javinttdata.view.MenuCliente.GestionClientes;
import org.javinttdata.operacion.repository.OperacionesRepository;
import org.javinttdata.view.MenuConsultas.GestionConsultas;
import org.javinttdata.view.MenuCuenta.GestionCuentas;
import org.javinttdata.view.MenuOperacion.GestionOperaciones;

/**
 * Clase que se encarga de mostrar el menu principal
 */
public class MenuPrincipal {

    //Declaramos los repositorios que vamos a usar
    private OperacionesRepository repositoryOp = null;

    private final GestionClientes gestionCl;
    private final GestionCuentas gestionCu;
    private final GestionOperaciones gestionOp;
    private final GestionConsultas gestionCo;



    public MenuPrincipal(GestionCuentas gestionCu, GestionClientes gestionCl, GestionOperaciones gestionOp, GestionConsultas gestionCo) {
        this.gestionCu = gestionCu;
        this.gestionCl = gestionCl;
        this.gestionOp = gestionOp;
        this.gestionCo = gestionCo;
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
                    gestionCl.opcionesMenu();
                    break;
                case 2:
                    gestionCu.opcionesMenu();
                    break;
                case 3:
                    gestionOp.opcionesMenu();
                    break;
                case 4:
                    gestionCo.opcionesMenu();
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
