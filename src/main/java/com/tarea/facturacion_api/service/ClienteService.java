package com.tarea.facturacion_api.service;

import com.tarea.facturacion_api.model.Cliente;
import com.tarea.facturacion_api.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    @Autowired // Inyecta el repositorio para usar la DB
    private ClienteRepository clienteRepository;

    public List<Cliente> listarClientes() {
        return clienteRepository.findAll();
    }

    public Optional<Cliente> obtenerClientePorId(Long id) {
        return clienteRepository.findById(id);
    }

    public Cliente crearCliente(Cliente cliente) {
        // Aquí se podrían añadir validaciones (ej. email único)
        return clienteRepository.save(cliente);
    }

    public Cliente actualizarCliente(Long id, Cliente clienteActualizado) {
        return clienteRepository.findById(id)
            .map(clienteExistente -> {
                clienteExistente.setNombre(clienteActualizado.getNombre());
                clienteExistente.setApellido(clienteActualizado.getApellido());
                clienteExistente.setDireccion(clienteActualizado.getDireccion());
                clienteExistente.setEmail(clienteActualizado.getEmail());
                return clienteRepository.save(clienteExistente);
            })
            .orElse(null); // O lanzar una excepción
    }

    public boolean eliminarCliente(Long id) {
        if (clienteRepository.existsById(id)) {
            clienteRepository.deleteById(id);
            return true;
        }
        return false;
    }
}