package org.javinttdata;

import org.javinttdata.cliente.repository.ClientesRepository;
import org.javinttdata.cliente.service.ClienteService;
import org.javinttdata.cuenta.repository.CuentaRepository;
import org.javinttdata.cuenta.service.CuentaService;
import org.javinttdata.operacion.service.OperacionService;
import org.javinttdata.view.MenuCliente.GestionClientes;
import org.javinttdata.view.MenuCuenta.GestionCuentas;
import org.javinttdata.view.MenuOperacion.GestionOperaciones;
import org.javinttdata.view.MenuPrincipal.MenuPrincipal;
import org.javinttdata.operacion.repository.OperacionesRepository;

public class Main {
    public static void main(String[] args) {
        CuentaRepository repositoryCu = new CuentaRepository();//Iniciamos repositorio de cuentas
        ClientesRepository repositoryCl = new ClientesRepository();//Iniciamos repositorio de clientes
        OperacionesRepository repositoryOp = new OperacionesRepository();//Iniciamos repositorio de operaciones



        //Repositorios
        ClientesRepository clienteRepo = new ClientesRepository();
        CuentaRepository cuentasRepo = new CuentaRepository();
        OperacionesRepository operacionesRepo = new OperacionesRepository();

        //Servicios
        ClienteService clienteService = new ClienteService(clienteRepo);
        CuentaService cuentaService = new CuentaService(cuentasRepo,clienteRepo);
        OperacionService operacionService = new OperacionService(cuentasRepo,operacionesRepo);

        //Menus
        GestionClientes menuClientes = new GestionClientes(clienteService);
        GestionCuentas menuCuentas = new GestionCuentas(cuentaService);
        GestionOperaciones menuOperaciones = new GestionOperaciones(operacionService);


        MenuPrincipal menu = new MenuPrincipal(menuCuentas, menuClientes, menuOperaciones);//Creamos instancia del menu para que pueda recibir los repositorios
        menu.OpcionesMenuP();//Llamamos al menu principal
    }
}