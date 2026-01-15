package com.tarea.facturacion_api.controller;

import com.tarea.facturacion_api.model.Factura;
import com.tarea.facturacion_api.service.FacturaService;
import com.tarea.facturacion_api.service.SriService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/facturas")
public class FacturaController {

    @Autowired
    private FacturaService facturaService;

    @Autowired
    private SriService sriService; // <--- 1. IMPORTANTE: Inyectamos el servicio del SRI

    // GET /facturas -> Listar todas
    @GetMapping
    public List<Factura> listarFacturas() {
        return facturaService.listarFacturas();
    }

    // GET /facturas/{id} -> Obtener una
    @GetMapping("/{id}")
    public ResponseEntity<Factura> obtenerFacturaPorId(@PathVariable Long id) {
        return facturaService.obtenerFacturaPorId(id)
                .map(factura -> new ResponseEntity<>(factura, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // POST /facturas -> Crear una factura
    @PostMapping
    public ResponseEntity<?> crearFactura(@RequestBody Factura factura) {
        try {
            System.out.println("--- INICIANDO PROCESO DE FACTURACIÓN ---"); // Log para depurar
            
            // 1. Guardar la factura en la base de datos (Stock, Totales)
            Factura nuevaFactura = facturaService.crearFactura(factura);
            System.out.println("Factura guardada ID: " + nuevaFactura.getId());
            
            // 2. AUTOMATIZACIÓN: Enviar inmediatamente al SRI (y esto dispara el WhatsApp)
            Factura facturaAutorizada = sriService.procesarFacturaElectronica(nuevaFactura.getId());
            System.out.println("Proceso SRI finalizado. Estado: " + facturaAutorizada.getEstadoSri());

            return new ResponseEntity<>(facturaAutorizada, HttpStatus.CREATED);
            
        } catch (RuntimeException e) {
            e.printStackTrace(); // Ver errores en consola
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}