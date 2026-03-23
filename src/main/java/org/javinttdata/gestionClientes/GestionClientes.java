package org.javinttdata.gestionClientes;

import org.javinttdata.Globales;
import org.javinttdata.menuPrincipal.MenuPrincipal;

import java.awt.*;
import java.sql.SQLOutput;

public class GestionClientes {
    static void MenuC()
    {
        System.out.println("--- GESTIÓN DE CLIENTES ---");
        System.out.println("1. Crear cliente");
        System.out.println("2. Buscar cliente");
        System.out.println("3. Listar cliente");
        System.out.println("4. Volver");
        System.out.println();
    }
    public static void OpcionesMenuC()
    {
        Globales.limpiarConsola();

        int opcion;

        do {
            MenuC();
            System.out.print("Seleccione una opción: ");
            try {
                opcion = Integer.parseInt(Globales.sc.nextLine());
            } catch (NumberFormatException e) {
                opcion = -1; //Valor por defecto si falla
            }
            switch (opcion) {
                case 1:

                    break;
                case 2:

                    break;
                case 3:

                    break;
                case 4:
                    System.out.println("Volviendo al menu");
                    try {//Hago una parada de 3 segundos para simular la carga de otra pantalla de la aplicacion
                        Thread.sleep(3000);
                        MenuPrincipal.OpcionesMenuP();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    break;
                default:
                    System.out.println("Opción no válida. Intente nuevamente.");
            }

            System.out.println();

        } while (opcion != 5);
    }
    static void CrearCliente()
    {

    }
}
