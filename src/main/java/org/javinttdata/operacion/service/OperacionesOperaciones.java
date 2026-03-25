package org.javinttdata.operacion.service;

import org.javinttdata.common.Globales;
import org.javinttdata.cuenta.model.Cuenta;
import org.javinttdata.cuenta.repository.CuentaRepository;
import org.javinttdata.operacion.model.Operacion;
import org.javinttdata.operacion.model.enums.TipoOperacion;
import org.javinttdata.operacion.repository.OperacionesRepository;

import java.util.ArrayList;

/**
 * Clase encargada de las acciones del menu de operaciones
 */
public class OperacionesOperaciones {

    /**
     * Metodo que se encarga de realizar el deposito en la cuenta que se especifique
     * @param repositoryCu repositorio de cuentas
     * @param repositoryOp repositorio de operaciones
     */
    static void Deposito(CuentaRepository repositoryCu, OperacionesRepository repositoryOp) {
        Globales.LimpiarConsola();
        System.out.print("Indica la cantidad a depositar: ");
        double cantidad;
        try {
            cantidad = Double.parseDouble(Globales.sc.nextLine());
        } catch (NumberFormatException e) {
            cantidad = -1; //Valor por defecto si falla
        }
        System.out.print("Indica el numero de cuenta: ");
        String nCuenta = Globales.sc.nextLine();

        Cuenta encontrada = repositoryCu.buscarPorIban(nCuenta);

        if (cantidad > 0 && encontrada != null) {
            encontrada.ingresar(cantidad);
            repositoryOp.operaciones//Esto lo he buscado en internet, lo que hace es que si no esta en el hash crea el arraylist y si esta solo añade
                    .computeIfAbsent(encontrada.getIban(), k -> new ArrayList<>())
                    .add(new Operacion(TipoOperacion.Deposito, cantidad));
            System.out.println("Depósito realizado correctamente.");
            System.out.println("Cuenta:      " + nCuenta);
            System.out.println("Importe:     +" + cantidad + "€");
            System.out.printf("Nuevo saldo: %,.2f €%n", encontrada.getSaldo());
        } else if (cantidad <= 0) {
            System.out.println("La cantidad debe de ser mayor a 0");
        } else {
            System.out.println("La cuenta no existe en la base de datos");
        }
        Globales.Continuar();
    }

    /**
     * Metodo que retira de una cuenta seleccionada una cantidad deseada
     * @param repositoryCu repositorio cuentas
     * @param repositoryOp repositorio operaciones
     */
    static void Retirar(CuentaRepository repositoryCu, OperacionesRepository repositoryOp) {
        Globales.LimpiarConsola();
        System.out.print("Indica la cantidad a retirar: ");
        double cantidad;
        try {
            cantidad = Double.parseDouble(Globales.sc.nextLine());
        } catch (NumberFormatException e) {
            cantidad = -1; //Valor por defecto si falla
        }
        System.out.print("Indica el numero de cuenta: ");
        String nCuenta = Globales.sc.nextLine();

        Cuenta encontrada = repositoryCu.buscarPorIban(nCuenta);

        if (cantidad > 0 && encontrada != null && encontrada.getSaldo() >= cantidad) {
            encontrada.retirar(cantidad);
            repositoryOp.operaciones
                    .computeIfAbsent(encontrada.getIban(), k -> new ArrayList<>())
                    .add(new Operacion(TipoOperacion.Retiro, -cantidad));
            System.out.println("Retiro realizado correctamente.");
            System.out.println("Cuenta:      " + nCuenta);
            System.out.println("Importe:     -" + cantidad + "€");
            System.out.printf("Nuevo saldo: %,.2f €%n", encontrada.getSaldo());
        } else if (cantidad <= 0) {
            System.out.println("La cantidad debe de ser mayor a 0");
        } else if (encontrada == null) {
            System.out.println("La cuenta no existe en la base de datos");
        } else {
            System.out.println("ERROR: Saldo insuficiente");
            System.out.printf("Saldo disponible: %,.2f €%n", encontrada.getSaldo());
            System.out.println("Importe solicitado: " + cantidad + "€");
        }
        Globales.Continuar();
    }

    /**
     * Metodo que se encarga de hacer una transferencia entre dos cuentas
     * @param repositoryCu repositorio cuentas
     * @param repositoryOp repositorio operaciones
     */
    static void Transferencia(CuentaRepository repositoryCu, OperacionesRepository repositoryOp) {
        Globales.LimpiarConsola();
        System.out.print("Indica la cantidad a transferir: ");
        double cantidad;
        try {
            cantidad = Double.parseDouble(Globales.sc.nextLine());
        } catch (NumberFormatException e) {
            cantidad = -1; //Valor por defecto si falla
        }
        System.out.print("Indica el numero de cuenta origen: ");
        String nCuentaO = Globales.sc.nextLine();

        System.out.print("Indica el numero de cuenta destino: ");
        String nCuentaD = Globales.sc.nextLine();

        Cuenta cO = repositoryCu.buscarPorIban(nCuentaO);
        Cuenta cD = repositoryCu.buscarPorIban(nCuentaD);


        if (cantidad > 0 && (cO != null && cD != null) && cO.getSaldo() >= cantidad) {
            cO.retirar(cantidad);
            cD.ingresar(cantidad);
            repositoryOp.operaciones
                    .computeIfAbsent(cO.getIban(), k -> new ArrayList<>())
                    .add(new Operacion(TipoOperacion.Transferencia_Saliente, -cantidad));
            repositoryOp.operaciones
                    .computeIfAbsent(cD.getIban(), k -> new ArrayList<>())
                    .add(new Operacion(TipoOperacion.Transferencia_Entrante, cantidad));

            System.out.println("Transferencia realizada correctamente.");
            System.out.println("Cuenta origen: " + nCuentaO + " -> " + " -" + cantidad + "€");
            System.out.println("Cuenta destino: " + nCuentaD + " -> " + " +" + cantidad + "€");


        } else if (cantidad <= 0) {
            System.out.println("La cantidad debe de ser mayor a 0");
        } else if (cO == null) {
            System.out.println("La cuenta origen no existe en la base de datos");
        } else if (cD == null) {
            System.out.println("La cuenta destino no existe en la base de datos");
        } else {
            System.out.println("ERROR: Saldo insuficiente");
            System.out.printf("Saldo disponible: %,.2f €%n", cO.getSaldo());
            System.out.println("Importe solicitado: " + cantidad + "€");
        }
        Globales.Continuar();
    }
}
