package org.javinttdata;

import java.util.Scanner;

public class Globales {
    //Un scanner global para toda la app
    public static Scanner sc = new Scanner(System.in);

    //Simulacion de limpieza de consola
    public static void LimpiarConsola() {
        System.out.println("\n".repeat(60));
    }
    public static void Continuar() {
        System.out.println("\nPulse ENTER para continuar...");
        sc.nextLine();
    }


}
