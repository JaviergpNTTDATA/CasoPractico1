package org.javinttdata.consulta.service;

import org.javinttdata.common.Globales;
import org.javinttdata.cuenta.repository.CuentaRepository;
import org.javinttdata.operaciones.repository.OperacionesRepository;
import org.javinttdata.operaciones.service.OperacionesOperaciones;


/**
 * Clase que se encarga de mostrar y gestionar las opciones del menu de consultas
 */
public class GestionConsultas {


    //Declaramos los repositorios que necesitaremos
    private final OperacionesRepository repositoryOp;
    private final CuentaRepository repositoryCu;

    /**
     * Asignamos los repositorios en el constructor
     *
     * @param repositoryOp repositorio de operaciones
     * @param repositoryCu repositorio de cuentas
     */
    public GestionConsultas(OperacionesRepository repositoryOp, CuentaRepository repositoryCu) {
        this.repositoryOp = repositoryOp;
        this.repositoryCu = repositoryCu;
    }

    /**
     * Metodo que se encarga de mostrar el menu
     */
    private void MenuC() {
        System.out.println("--- CONSULTAS ---");
        System.out.println("1. Consultar saldo");
        System.out.println("2. Historial de movimientos");
        System.out.println("3. Movimientos por rango de fecha");
        System.out.println("4. Volver");
        System.out.println();
    }

    /**
     * Metodo que gestiona la seleccion de opciones del menu
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
                    OperacionesConsultas.ConsultarSaldo(repositoryCu);
                    break;
                case 2:
                    OperacionesConsultas.HistorialMov(repositoryOp, repositoryCu);
                    break;
                case 3:
                    OperacionesConsultas.MovPorFechas(repositoryOp, repositoryCu);
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

