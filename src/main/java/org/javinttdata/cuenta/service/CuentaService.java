package org.javinttdata.cuenta.service;

import org.javinttdata.cliente.model.Cliente;
import org.javinttdata.cliente.repository.ClientesRepository;
import org.javinttdata.cuenta.model.Cuenta;
import org.javinttdata.cuenta.repository.CuentaRepository;

import java.util.List;

public class CuentaService {

    private final CuentaRepository cuentaRepository;
    private final ClientesRepository clientesRepository;

    public CuentaService(CuentaRepository cuentaRepository,
                         ClientesRepository clientesRepository) {
        this.cuentaRepository = cuentaRepository;
        this.clientesRepository = clientesRepository;
    }

    public Cuenta crearCuenta(long idCliente) {

        Cliente cliente = clientesRepository.buscarPorId(idCliente);

        if (cliente == null) {
            return null;
        }

        Cuenta nuevaCuenta = new Cuenta(cliente.getId());
        cuentaRepository.guardar(nuevaCuenta);

        return nuevaCuenta;
    }

    public List<Cuenta> listarCuentasCliente(long idCliente) {
        return cuentaRepository.buscarPorIdCliente(idCliente);
    }

    public Cliente buscarCliente(long id) {
        return clientesRepository.buscarPorId(id);
    }

    public Cuenta buscarPorIban(String iban) {
        return cuentaRepository.buscarPorIban(iban);
    }
}
