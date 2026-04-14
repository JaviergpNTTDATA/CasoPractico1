package org.javinttdata.cuenta.service;

import org.javinttdata.cliente.model.Cliente;
import org.javinttdata.cliente.repository.ClienteRepository;
import org.javinttdata.cuenta.model.Cuenta;
import org.javinttdata.cuenta.repository.CuentaRepository;

import java.util.List;
import java.util.Optional;

public class CuentaService {

    private final CuentaRepository cuentaRepository;
    private final ClienteRepository clientesRepository;

    public CuentaService(CuentaRepository cuentaRepository,
                         ClienteRepository clientesRepository) {
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
        return cuentaRepository.buscarPorIdCliente(idCliente);
    }

    public Cliente buscarCliente(long id) {
        return clientesRepository.buscarPorId(id).orElse(null);
    }

    public Cuenta buscarPorIban(String iban) {
        return cuentaRepository.buscarPorIban(iban);
    }
}
