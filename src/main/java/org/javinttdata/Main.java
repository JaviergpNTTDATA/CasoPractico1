package org.javinttdata;

import org.javinttdata.Cliente.ClientesRepository;
import org.javinttdata.menuPrincipal.MenuPrincipal;

public class Main {
    public static void main(String[] args) {
        ClientesRepository repository = new ClientesRepository();
        MenuPrincipal menu = new MenuPrincipal(repository);
        menu.OpcionesMenuP();
    }
}