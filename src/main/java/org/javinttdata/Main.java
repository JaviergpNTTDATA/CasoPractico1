package org.javinttdata;

import org.javinttdata.cliente.repository.ClientesRepository;
import org.javinttdata.cuenta.repository.CuentaRepository;
import org.javinttdata.common.menuPrincipal.MenuPrincipal;
import org.javinttdata.operaciones.repository.OperacionesRepository;

public class Main {
    public static void main(String[] args) {
        CuentaRepository repositoryCu = new CuentaRepository();//Iniciamos repositorio de cuentas
        ClientesRepository repositoryCl = new ClientesRepository();//Iniciamos repositorio de clientes
        OperacionesRepository repositoryOp = new OperacionesRepository();//Iniciamos repositorio de operaciones
        MenuPrincipal menu = new MenuPrincipal(repositoryCu, repositoryCl, repositoryOp);//Creamos instancia del menu para que pueda recibir los repositorios
        menu.OpcionesMenuP();//Llamamos al menu principal
    }
}