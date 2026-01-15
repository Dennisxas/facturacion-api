package com.tarea.facturacion_api.controller;

import com.tarea.facturacion_api.model.Factura;
import com.tarea.facturacion_api.service.SriService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sri")
public class SriController {

    @Autowired
    private SriService sriService;

    // Endpoint: POST /sri/autorizar/{idFactura}
    // Simula el envío al SRI. Devuelve la factura actualizada con Clave de Acceso y Estado.
    @PostMapping("/autorizar/{id}")
    public ResponseEntity<Factura> enviarFacturaSri(@PathVariable Long id) {
        Factura facturaProcesada = sriService.procesarFacturaElectronica(id);
        return ResponseEntity.ok(facturaProcesada);
    }
}
