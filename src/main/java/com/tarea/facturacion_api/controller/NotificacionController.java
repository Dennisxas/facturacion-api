package com.tarea.facturacion_api.controller;

import com.tarea.facturacion_api.model.Factura;
import com.tarea.facturacion_api.service.FacturaService;
import com.tarea.facturacion_api.service.WhatsappService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/notificaciones")
public class NotificacionController {

    @Autowired
    private WhatsappService whatsappService;

    @Autowired
    private FacturaService facturaService;

    // Endpoint: POST /notificaciones/whatsapp/{idFactura}
    @PostMapping("/whatsapp/{id}")
    public ResponseEntity<String> enviarWhatsapp(@PathVariable Long id) {
        Factura factura = facturaService.obtenerFacturaPorId(id)
                .orElseThrow(() -> new RuntimeException("Factura no encontrada"));

        whatsappService.enviarNotificacion(factura);

        return ResponseEntity.ok("Notificación enviada correctamente a WhatsApp.");
    }
}