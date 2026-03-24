package org.javinttdata;

import org.javinttdata.cliente.repository.ClientesRepository;
import org.javinttdata.cuenta.repository.CuentaRepository;
import org.javinttdata.common.menuPrincipal.MenuPrincipal;
import org.javinttdata.operaciones.repository.OperacionesRepository;

public class Main {
    public static void main(String[] args) {
        CuentaRepository repositoryCu = new CuentaRepository();
        ClientesRepository repositoryCl = new ClientesRepository();
        OperacionesRepository repositoryOp = new OperacionesRepository();
        MenuPrincipal menu = new MenuPrincipal(repositoryCu, repositoryCl, repositoryOp);
        menu.OpcionesMenuP();
    }
}