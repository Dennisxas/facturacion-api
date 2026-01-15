package com.tarea.facturacion_api.controller;

import com.tarea.facturacion_api.model.Cliente;
import com.tarea.facturacion_api.model.Factura;
import com.tarea.facturacion_api.model.Producto;
import com.tarea.facturacion_api.service.ClienteService;
import com.tarea.facturacion_api.service.FacturaService;
import com.tarea.facturacion_api.service.PdfService;
import com.tarea.facturacion_api.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.util.List;

@RestController
@RequestMapping("/reportes")
public class ReporteController {

    @Autowired
    private PdfService pdfService;
    @Autowired
    private FacturaService facturaService;
    @Autowired
    private ClienteService clienteService; // Inyectamos servicio de clientes
    @Autowired
    private ProductoService productoService; // Inyectamos servicio de productos

    // 1. FACTURA INDIVIDUAL (Ya existente)
    @GetMapping("/factura/{id}/pdf")
    public ResponseEntity<InputStreamResource> descargarFacturaPdf(@PathVariable Long id) {
        Factura factura = facturaService.obtenerFacturaPorId(id)
                .orElseThrow(() -> new RuntimeException("Factura no encontrada"));

        ByteArrayInputStream pdfStream = pdfService.generarFacturaPdf(factura);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=factura_" + id + ".pdf");

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(pdfStream));
    }

    // 2. REPORTE DE TODOS LOS CLIENTES (Nuevo)
    @GetMapping("/clientes/pdf")
    public ResponseEntity<InputStreamResource> descargarReporteClientes() {
        List<Cliente> clientes = clienteService.listarClientes();
        ByteArrayInputStream pdfStream = pdfService.generarReporteClientes(clientes);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=reporte_clientes.pdf");

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(pdfStream));
    }

    // 3. REPORTE DE INVENTARIO (PRODUCTOS) (Nuevo)
    @GetMapping("/productos/pdf")
    public ResponseEntity<InputStreamResource> descargarReporteProductos() {
        List<Producto> productos = productoService.listarProductos();
        ByteArrayInputStream pdfStream = pdfService.generarReporteProductos(productos);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=reporte_productos.pdf");

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(pdfStream));
    }
}