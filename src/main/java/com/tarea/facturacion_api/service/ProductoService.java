package com.tarea.facturacion_api.service;

import com.tarea.facturacion_api.model.Producto;
import com.tarea.facturacion_api.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    public List<Producto> listarProductos() {
        return productoRepository.findAll();
    }

    public Optional<Producto> obtenerProductoPorId(Long id) {
        return productoRepository.findById(id);
    }

    public Producto crearProducto(Producto producto) {
        // Aquí se podrían añadir validaciones (ej. precio > 0)
        return productoRepository.save(producto);
    }

    public Producto actualizarProducto(Long id, Producto productoActualizado) {
        return productoRepository.findById(id)
            .map(productoExistente -> {
                productoExistente.setNombre(productoActualizado.getNombre());
                productoExistente.setDescripcion(productoActualizado.getDescripcion());
                productoExistente.setPrecio(productoActualizado.getPrecio());
                productoExistente.setStock(productoActualizado.getStock());
                return productoRepository.save(productoExistente);
            })
            .orElse(null);
    }

    public boolean eliminarProducto(Long id) {
        if (productoRepository.existsById(id)) {
            productoRepository.deleteById(id);
            return true;
        }
        return false;
    }
}