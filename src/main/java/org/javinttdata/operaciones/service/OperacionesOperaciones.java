package org.javinttdata.operaciones.service;

import org.javinttdata.common.Globales;
import org.javinttdata.cuenta.model.Cuenta;
import org.javinttdata.cuenta.repository.CuentaRepository;
import org.javinttdata.operaciones.model.Operacion;
import org.javinttdata.operaciones.model.enums.TipoOperacion;
import org.javinttdata.operaciones.repository.OperacionesRepository;

import java.util.ArrayList;

public class OperacionesOperaciones {

    static void Deposito(CuentaRepository repositoryCu, OperacionesRepository repositoryOp) {
        Globales.LimpiarConsola();
        System.out.print("Indica la cantidad a depositar: ");
        int cantidad;
        try {
            cantidad = Integer.parseInt(Globales.sc.nextLine());
        } catch (NumberFormatException e) {
            cantidad = -1; //Valor por defecto si falla
        }
        System.out.print("Indica el numero de cuenta: ");
        String nCuenta = Globales.sc.nextLine();

        Cuenta encontrada = repositoryCu.cuentasPorIban.get(nCuenta);

        if (cantidad > 0 && encontrada != null) {
            encontrada.setSaldo(cantidad);
            repositoryOp.operaciones//Esto lo he buscado en internet, lo que hace es que si no esta en el hash crea el arraylist y si esta solo añade
                    .computeIfAbsent(encontrada.getIban(), k -> new ArrayList<>())
                    .add(new Operacion(TipoOperacion.Deposito, cantidad));
            System.out.println("Depósito realizado correctamente.");
            System.out.println("Cuenta:      " + nCuenta);
            System.out.println("Importe:     +" + cantidad + "€");
            System.out.println("Nuevo saldo: " + encontrada.getSaldo() + "€");
        } else if (cantidad <= 0) {
            System.out.println("La cantidad debe de ser mayor a 0");
        } else {
            System.out.println("La cuenta no existe en la base de datos");
        }
        Globales.Continuar();
    }

    static void Retirar(CuentaRepository repositoryCu, OperacionesRepository repositoryOp) {
        Globales.LimpiarConsola();
        System.out.print("Indica la cantidad a retirar: ");
        int cantidad;
        try {
            cantidad = Integer.parseInt(Globales.sc.nextLine());
        } catch (NumberFormatException e) {
            cantidad = -1; //Valor por defecto si falla
        }
        System.out.print("Indica el numero de cuenta: ");
        String nCuenta = Globales.sc.nextLine();

        Cuenta encontrada = repositoryCu.cuentasPorIban.get(nCuenta);

        if (cantidad > 0 && encontrada != null && encontrada.getSaldo() >= cantidad) {
            encontrada.setSaldo(-cantidad);
            repositoryOp.operaciones
                    .computeIfAbsent(encontrada.getIban(), k -> new ArrayList<>())
                    .add(new Operacion(TipoOperacion.Retiro, -cantidad));
            System.out.println("Retiro realizado correctamente.");
            System.out.println("Cuenta:      " + nCuenta);
            System.out.println("Importe:     -" + cantidad + "€");
            System.out.println("Nuevo saldo: " + encontrada.getSaldo() + "€");
        } else if (cantidad <= 0) {
            System.out.println("La cantidad debe de ser mayor a 0");
        } else if (encontrada == null) {
            System.out.println("La cuenta no existe en la base de datos");
        } else {
            System.out.println("ERROR: Saldo insuficiente");
            System.out.println("Saldo disponible: " + encontrada.getSaldo() + "€");
            System.out.println("Importe solicitado: " + cantidad + "€");
        }
        Globales.Continuar();
    }

    static void Transferencia(CuentaRepository repositoryCu, OperacionesRepository repositoryOp) {
        Globales.LimpiarConsola();
        System.out.print("Indica la cantidad a transferir: ");
        int cantidad;
        try {
            cantidad = Integer.parseInt(Globales.sc.nextLine());
        } catch (NumberFormatException e) {
            cantidad = -1; //Valor por defecto si falla
        }
        System.out.print("Indica el numero de cuenta origen: ");
        String nCuentaO = Globales.sc.nextLine();

        System.out.print("Indica el numero de cuenta destino: ");
        String nCuentaD = Globales.sc.nextLine();

        Cuenta cO = repositoryCu.cuentasPorIban.get(nCuentaO);
        Cuenta cD = repositoryCu.cuentasPorIban.get(nCuentaD);


        if (cantidad > 0 && (cO != null && cD != null) && cO.getSaldo() >= cantidad) {
            cO.setSaldo(-cantidad);
            cD.setSaldo(cantidad);
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
            System.out.println("Saldo disponible: " + cO.getSaldo() + "€");
            System.out.println("Importe solicitado: " + cantidad + "€");
        }
        Globales.Continuar();
    }
}
