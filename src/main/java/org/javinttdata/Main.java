package org.javinttdata;

import org.javinttdata.cliente.repository.ClientesRepositoryJdbc;
import org.javinttdata.cliente.service.ClienteService;
import org.javinttdata.consulta.service.ConsultaService;
import org.javinttdata.cuenta.repository.CuentaRepositoryJdbc;
import org.javinttdata.cuenta.service.CuentaService;
import org.javinttdata.operacion.service.OperacionService;
import org.javinttdata.view.MenuCliente.GestionClientes;
import org.javinttdata.view.MenuConsultas.GestionConsultas;
import org.javinttdata.view.MenuCuenta.GestionCuentas;
import org.javinttdata.view.MenuOperacion.GestionOperaciones;
import org.javinttdata.view.MenuPrincipal.MenuPrincipal;
import org.javinttdata.operacion.repository.OperacionesRepositoryJdbc;

public class Main {
    public static void main(String[] args) {
        CuentaRepositoryJdbc repositoryCu = new CuentaRepositoryJdbc();//Iniciamos repositorio de cuentas
        ClientesRepositoryJdbc repositoryCl = new ClientesRepositoryJdbc();//Iniciamos repositorio de clientes
        OperacionesRepositoryJdbc repositoryOp = new OperacionesRepositoryJdbc();//Iniciamos repositorio de operaciones



        //Repositorios
        ClientesRepositoryJdbc clienteRepo = new ClientesRepositoryJdbc();
<<<<<<< HEAD
        CuentaRepositoryJdbc cuentasRepo = new CuentaRepositoryJdbc();
        OperacionesRepository operacionesRepo = new OperacionesRepository();
=======
        CuentaRepository cuentasRepo = new CuentaRepository();
        OperacionesRepositoryJdbc operacionesRepo = new OperacionesRepositoryJdbc();
>>>>>>> 4660014 (Commit final de la capa operacion(a priori))

        //Servicios
        ClienteService clienteService = new ClienteService(clienteRepo);
        CuentaService cuentaService = new CuentaService(cuentasRepo,clienteRepo);
        OperacionService operacionService = new OperacionService(cuentasRepo,operacionesRepo);
        ConsultaService consultaService = new ConsultaService(cuentasRepo,operacionesRepo);

        //Menus
        GestionClientes menuClientes = new GestionClientes(clienteService);
        GestionCuentas menuCuentas = new GestionCuentas(cuentaService);
        GestionOperaciones menuOperaciones = new GestionOperaciones(operacionService);
        GestionConsultas menuConsultas = new GestionConsultas(consultaService);


        MenuPrincipal menu = new MenuPrincipal(menuCuentas, menuClientes, menuOperaciones, menuConsultas);//Creamos instancia del menu para que pueda recibir los repositorios
        menu.OpcionesMenuP();//Llamamos al menu principal
    }
}