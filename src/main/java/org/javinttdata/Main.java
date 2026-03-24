package org.javinttdata;

import org.javinttdata.cliente.repository.ClientesRepository;
import org.javinttdata.cuenta.repository.CuentaRepository;
import org.javinttdata.common.menuPrincipal.MenuPrincipal;

public class Main {
    public static void main(String[] args) {
        CuentaRepository repositoryCu = new CuentaRepository();
        ClientesRepository repositoryCl = new ClientesRepository();
        MenuPrincipal menu = new MenuPrincipal(repositoryCu, repositoryCl);
        menu.OpcionesMenuP();
    }
}