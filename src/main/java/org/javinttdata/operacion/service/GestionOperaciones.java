package org.javinttdata.operacion.service;

import org.javinttdata.cuenta.repository.CuentaRepository;
import org.javinttdata.common.Globales;
import org.javinttdata.operacion.repository.OperacionesRepository;

/**
 * Claso encargada de las acciones del menu de operaciones
 */
public class GestionOperaciones {

    //Declaramos los repositorios que vamos a usar
    private final OperacionesRepository repositoryOp;
    private final CuentaRepository repositoryCu;

    /**
     * En el constructor asignamos los repositorios
     * @param repositoryOp repositorio de operaciones
     * @param repositoryCu repositorio de cuentas
     */
    public GestionOperaciones(OperacionesRepository repositoryOp, CuentaRepository repositoryCu) {
        this.repositoryOp = repositoryOp;
        this.repositoryCu = repositoryCu;
    }

    /**
     * Metodo que muestra el menu
     */
    private void MenuC() {
        System.out.println("--- OPERACIONES FINANCIERAS ---");
        System.out.println("1. Depositar dinero");
        System.out.println("2. Retirar dinero");
        System.out.println("3. Transferencia entre cuentas");
        System.out.println("4. Volver");
        System.out.println();
    }

    /**
     * Metodo que se encarga de gestionar las opciones del menu
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
                    OperacionesOperaciones.Deposito(repositoryCu,repositoryOp);
                    break;
                case 2:
                    OperacionesOperaciones.Retirar(repositoryCu,repositoryOp);
                    break;
                case 3:
                    OperacionesOperaciones.Transferencia(repositoryCu,repositoryOp);
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