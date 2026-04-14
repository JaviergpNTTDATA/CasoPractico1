package org.javinttdata;

import org.javinttdata.cliente.repository.ClientesRepositoryJdbc;
import org.javinttdata.cliente.service.ClienteService;
import org.javinttdata.consulta.service.ConsultaService;
import org.javinttdata.cuenta.repository.CuentaRepository;
import org.javinttdata.cuenta.repository.CuentaRepositoryJdbc;
import org.javinttdata.cuenta.service.CuentaService;
import org.javinttdata.operacion.repository.OperacionesRepositoryJdbc;
import org.javinttdata.operacion.service.OperacionService;
import org.javinttdata.view.MenuCliente.GestionClientes;
import org.javinttdata.view.MenuConsultas.GestionConsultas;
import org.javinttdata.view.MenuCuenta.GestionCuentas;
import org.javinttdata.view.MenuOperacion.GestionOperaciones;
import org.javinttdata.view.MenuPrincipal.MenuPrincipal;

public class Main {

    public static void main(String[] args) {

        // Repositorios
        ClientesRepositoryJdbc clienteRepo = new ClientesRepositoryJdbc();
        CuentaRepositoryJdbc cuentasRepo = new CuentaRepositoryJdbc();
        OperacionesRepositoryJdbc operacionesRepo = new OperacionesRepositoryJdbc();

        // Servicios
        ClienteService clienteService = new ClienteService(clienteRepo);
        CuentaService cuentaService = new CuentaService(cuentasRepo, clienteRepo);
        OperacionService operacionService = new OperacionService(cuentasRepo, operacionesRepo);
        ConsultaService consultaService = new ConsultaService(cuentasRepo, operacionesRepo);

        // Menús
        GestionClientes menuClientes = new GestionClientes(clienteService);
        GestionCuentas menuCuentas = new GestionCuentas(cuentaService);
        GestionOperaciones menuOperaciones = new GestionOperaciones(operacionService);
        GestionConsultas menuConsultas = new GestionConsultas(consultaService);

        MenuPrincipal menu = new MenuPrincipal(menuCuentas, menuClientes, menuOperaciones, menuConsultas
        );

        menu.OpcionesMenuP();
    }
}
