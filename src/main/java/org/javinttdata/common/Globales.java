package org.javinttdata.common;

import java.util.Scanner;

/**
 * Clase en la que usaremos aspectos globales para todos los metodos
 */
public class Globales {
    //Un scanner global para toda la app
    public static Scanner sc = new Scanner(System.in);

    /**
     * Simulacion de limpieza de consola
     */
    public static void LimpiarConsola() {
        System.out.println("\n".repeat(60));
    }

    /**
     * Metodo para confirmar un cambio de menu
     */
    public static void Continuar() {
        System.out.println("\nPulse ENTER para continuar...");
        sc.nextLine();
    }


}
