package org.javinttdata;

import org.javinttdata.Cuenta.CuentaRepository;
import org.javinttdata.menuPrincipal.MenuPrincipal;

public class Main {
    public static void main(String[] args) {
        CuentaRepository repository = new CuentaRepository();
        MenuPrincipal menu = new MenuPrincipal(repository);
        menu.OpcionesMenuP();
    }
}