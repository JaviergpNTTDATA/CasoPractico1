package org.javinttdata.cuenta.service;

import org.javinttdata.cliente.model.Cliente;
import org.javinttdata.cliente.repository.ClienteRepository;
import org.javinttdata.cliente.repository.ClientesRepositoryJdbc;
import org.javinttdata.cuenta.model.Cuenta;
import org.javinttdata.cuenta.repository.CuentaRepository;
import org.javinttdata.cuenta.repository.CuentaRepositoryJdbc;

import java.util.List;
import java.util.Optional;

public class CuentaService {

    //Declaramos los repositorios que vamos a usar
    private final CuentaRepository cuentaRepository;
    private final ClienteRepository clientesRepository;

    public CuentaService(CuentaRepository cuentaRepository, ClienteRepository clientesRepository) {
        this.cuentaRepository = cuentaRepository;
        this.clientesRepository = clientesRepository;
    }

    public Cuenta crearCuenta(long idCliente) {

        return clientesRepository.buscarPorId(idCliente)
                .map(cliente -> {
                    Cuenta nuevaCuenta = new Cuenta(cliente.getId());
                    return cuentaRepository.guardar(nuevaCuenta);
                })
                .orElse(null);
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
