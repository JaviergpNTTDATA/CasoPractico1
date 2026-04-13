package org.javinttdata;

import org.javinttdata.cliente.repository.ClientesRepository;
import org.javinttdata.cliente.service.ClienteService;
import org.javinttdata.cuenta.repository.CuentaRepository;
import org.javinttdata.view.MenuCliente.GestionClientes;
import org.javinttdata.view.MenuPrincipal.MenuPrincipal;
import org.javinttdata.operacion.repository.OperacionesRepository;

public class Main {
    public static void main(String[] args) {
        CuentaRepository repositoryCu = new CuentaRepository();//Iniciamos repositorio de cuentas
        ClientesRepository repositoryCl = new ClientesRepository();//Iniciamos repositorio de clientes
        OperacionesRepository repositoryOp = new OperacionesRepository();//Iniciamos repositorio de operaciones



        //Repositorios
        ClientesRepository clienteRepo = new ClientesRepository();

        //Servicios
        ClienteService clienteService = new ClienteService(clienteRepo);

        //Menus
        GestionClientes menuClientes = new GestionClientes(clienteService);



        MenuPrincipal menu = new MenuPrincipal(menuClientes);//Creamos instancia del menu para que pueda recibir los repositorios
        menu.OpcionesMenuP();//Llamamos al menu principal
    }
}