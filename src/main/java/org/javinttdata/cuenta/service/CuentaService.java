package org.javinttdata.cuenta.service;

import org.javinttdata.cliente.model.Cliente;
import org.javinttdata.cliente.repository.ClienteRepository;
import org.javinttdata.cliente.repository.ClientesRepositoryJdbc;
import org.javinttdata.cuenta.model.Cuenta;
import org.javinttdata.cuenta.repository.CuentaRepositoryJdbc;

import java.util.List;
import java.util.Optional;

public class CuentaService {

    //Declaramos los repositorios que vamos a usar
    private final CuentaRepositoryJdbc cuentaRepository;
    private final ClientesRepositoryJdbc clientesRepository;

    public CuentaService(CuentaRepositoryJdbc cuentaRepository, ClientesRepositoryJdbc clientesRepository) {
        this.cuentaRepository = cuentaRepository;
        this.clientesRepository = clientesRepository;
    }

    public Cuenta crearCuenta(long idCliente) {

        Optional<Cliente> clienteOpt = clientesRepository.buscarPorId(idCliente);

        if (clienteOpt.isEmpty()) {
            return null;
        }

        Cuenta nuevaCuenta = new Cuenta(clienteOpt.get().getId());
        cuentaRepository.guardar(nuevaCuenta);

        return nuevaCuenta;
    }

    public List<Cuenta> listarCuentasCliente(long idCliente) {
        return cuentaRepository.buscarPorClienteId(idCliente);
    }

    public Cliente buscarCliente(long id) {
        return clientesRepository.buscarPorId(id).orElse(null);
    }

    public Cuenta buscarPorIban(String iban) {
        return cuentaRepository.buscarPorNumero(iban)
                .orElse(null);
    }

}
