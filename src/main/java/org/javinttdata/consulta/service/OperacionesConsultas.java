package org.javinttdata.consulta.service;

import org.javinttdata.common.Globales;
import org.javinttdata.cuenta.model.Cuenta;
import org.javinttdata.cuenta.repository.CuentaRepository;
import org.javinttdata.operaciones.model.Operacion;
import org.javinttdata.operaciones.repository.OperacionesRepository;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


/**
 * Clase que gestiona los metodos que se ejecutan en el menu
 */
public class OperacionesConsultas {
    //Declaramos formatos de fechas que usaremos mas tarde
    private static final DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static final DateTimeFormatter formatoDia = DateTimeFormatter.ofPattern("yyyy-MM-dd");



    /**
     * Metodo que se encarga de consultar el saldo de una cuenta
     * @param repository repositorio de cuentas
     */
    static void ConsultarSaldo(CuentaRepository repository) {
        Globales.LimpiarConsola();
        System.out.print("Introduzca número de cuenta: ");
        String nCuenta = Globales.sc.nextLine();

        Cuenta encontrada = repository.cuentasPorIban.get(nCuenta);

        if (encontrada != null) {
            System.out.println("Saldo actual: " + encontrada.getSaldo() + "€");
        } else {
            System.out.println("ERROR: La cuenta no existe");
        }
        Globales.Continuar();
    }

    /**
     * Metodo que se encarga de mostrar todos los movimientos de una cuenta
     * @param repositoryOp repositorio de operaciones
     * @param repositoryCu repositorio de cuentas
     */
    static void HistorialMov(OperacionesRepository repositoryOp, CuentaRepository repositoryCu) {
        Globales.LimpiarConsola();
        System.out.print("Introduzca número de cuenta: ");
        String nCuenta = Globales.sc.nextLine();

        Cuenta encontrada = repositoryCu.cuentasPorIban.get(nCuenta);

        if (encontrada != null) {
            List<Operacion> listaOp = repositoryOp.operaciones.get(encontrada.getIban());
            System.out.println("Historial de movimientos - " + nCuenta);
            NumberFormat formatoEuro = NumberFormat.getCurrencyInstance(new Locale("es", "ES"));

            System.out.printf("%-20s | %-22s | %-12s%n", "Fecha", "Tipo", "Cantidad");
            System.out.println("---------------------|------------------------|------------");

            for (Operacion op : listaOp) {

                String fecha = op.getFecha().format(formato);
                String tipo = op.getTipoO().name();

                double cantidad = op.getCantidad();

                System.out.printf("%-20s | %-22s | %-12s € %n", fecha, tipo.toUpperCase(), cantidad);
            }

        } else {
            System.out.println("ERROR: La cuenta no existe");
        }
        Globales.Continuar();
    }

    /**
     * Metodo que se encarga de mostrar los movimientos de una cuenta comprendidos entre dos fecha
     * @param repositoryOp repositorio de operaciones
     * @param repositoryCu repositorio de cuentas
     */
    public static void MovPorFechas(OperacionesRepository repositoryOp, CuentaRepository repositoryCu) {

        Globales.LimpiarConsola();

        System.out.print("Número de cuenta: ");
        String nCuenta = Globales.sc.nextLine();

        Cuenta encontrada = repositoryCu.cuentasPorIban.get(nCuenta);

        if (encontrada != null) {
            System.out.print("Fecha inicio (yyyy-MM-dd): ");
            LocalDate fechaInicio = LocalDate.parse(Globales.sc.nextLine(), formatoDia);

            System.out.print("Fecha fin (yyyy-MM-dd): ");
            LocalDate fechaFin = LocalDate.parse(Globales.sc.nextLine(), formatoDia);

            System.out.println();
            List<Operacion> filtradas = new ArrayList<>();

            for (Operacion op : repositoryOp.operaciones.get(encontrada.getIban())) {
                LocalDate fechaOperacion = op.getFecha().toLocalDate();

                if (!fechaOperacion.isBefore(fechaInicio) && !fechaOperacion.isAfter(fechaFin)) {

                    filtradas.add(op);
                }
            }
            if (filtradas.isEmpty()) {
                System.out.println("No hay movimientos en ese rango.");
            } else {
                System.out.println("Movimientos del " + fechaInicio + " al " + fechaFin + ":");
                System.out.printf("%-20s | %-22s | %-12s%n", "Fecha", "Tipo", "Cantidad");
                System.out.println("---------------------|------------------------|------------");

                for (Operacion op : filtradas) {

                    String fecha = op.getFecha().format(formato);
                    String tipo = op.getTipoO().name();

                    double cantidad = op.getCantidad();

                    System.out.printf("%-20s | %-22s | %-12s € %n", fecha, tipo.toUpperCase(), cantidad);
                }
            }
        }
        Globales.Continuar();
    }
}
