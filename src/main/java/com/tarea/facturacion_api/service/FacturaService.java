package com.tarea.facturacion_api.service;

import com.tarea.facturacion_api.model.Cliente;
import com.tarea.facturacion_api.model.Factura;
import com.tarea.facturacion_api.model.DetalleFactura;
import com.tarea.facturacion_api.model.Producto;
import com.tarea.facturacion_api.repository.ClienteRepository;
import com.tarea.facturacion_api.repository.FacturaRepository;
import com.tarea.facturacion_api.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // Importante

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class FacturaService {

    @Autowired
    private FacturaRepository facturaRepository;
    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private ProductoRepository productoRepository;

    // --- Lógica de Negocio (CRUD de Facturas) ---

    public List<Factura> listarFacturas() {
        return facturaRepository.findAll();
    }

    public Optional<Factura> obtenerFacturaPorId(Long id) {
        return facturaRepository.findById(id);
    }

    // --- LÓGICA DE NEGOCIO CLAVE: CREAR FACTURA ---
    // @Transactional: Si algo falla (ej. no hay stock),
    // se revierte toda la operación (no se guarda nada).
    @Transactional
    public Factura crearFactura(Factura factura) {

        // 1. Validar Cliente
        Cliente cliente = clienteRepository.findById(factura.getCliente().getId())
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
        factura.setCliente(cliente);

        double totalFactura = 0.0;

        // 2. Procesar cada línea de detalle
        for (DetalleFactura detalle : factura.getDetalles()) {

            // 3. Validar Producto y Stock
            Producto producto = productoRepository.findById(detalle.getProducto().getId())
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

            if (producto.getStock() < detalle.getCantidad()) {
                throw new RuntimeException("Stock insuficiente para el producto: " + producto.getNombre());
            }

            // 4. Actualizar Stock
            producto.setStock(producto.getStock() - detalle.getCantidad());
            productoRepository.save(producto);

            // 5. Asignar precio y calcular subtotal
            detalle.setPrecioUnitario(producto.getPrecio()); // Precio al momento de la venta
            double subtotalDetalle = detalle.getCantidad() * detalle.getPrecioUnitario();
            totalFactura += subtotalDetalle;

            // 6. Vincular el detalle con la factura (importante para JPA)
            detalle.setFactura(factura);
        }

        // 7. Asignar datos finales y guardar
        factura.setTotal(totalFactura);
        factura.setFecha(LocalDateTime.now());

        // Al guardar la factura, JPA (gracias a CascadeType.ALL)
        // guardará automáticamente todos los detalles asociados.
        return facturaRepository.save(factura);
    }

    // Nota: La tarea solo pide crear y consultar facturas, 
    // por lo que omitimos 'actualizar' y 'eliminar' facturas, 
    // ya que son operaciones complejas (requieren re-abastecer stock, etc.).
    // Si necesitas eliminar, se añadiría aquí.
}