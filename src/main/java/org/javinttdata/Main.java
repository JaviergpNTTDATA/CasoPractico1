package org.javinttdata;

import org.javinttdata.Cliente.ClientesRepository;
import org.javinttdata.Cuenta.CuentaRepository;
import org.javinttdata.menuPrincipal.MenuPrincipal;

public class Main {
    public static void main(String[] args) {
        CuentaRepository repositoryCu = new CuentaRepository();
        ClientesRepository repositoryCl = new ClientesRepository();
        MenuPrincipal menu = new MenuPrincipal(repositoryCu, repositoryCl);
        menu.OpcionesMenuP();
    }
}