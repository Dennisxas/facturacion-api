package com.tarea.facturacion_api.controller;

import com.tarea.facturacion_api.model.Configuracion;
import com.tarea.facturacion_api.repository.ConfiguracionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/configuracion")
public class ConfiguracionController {

    @Autowired
    private ConfiguracionRepository configRepo;

    @GetMapping
    public ResponseEntity<Configuracion> obtenerConfig() {
        // Siempre devolvemos el ID 1 (Unica configuración)
        return configRepo.findById(1L)
                .map(ResponseEntity::ok)
                .orElseGet(() -> {
                    // Si no existe, creamos una por defecto
                    Configuracion def = new Configuracion();
                    def.setNombreEmpresa("MI EMPRESA S.A.");
                    def.setRuc("9999999999001");
                    def.setDireccion("Matriz Ecuador");
                    def.setTelefono("0999999999");
                    def.setIvaPorcentaje(15.0);
                    return ResponseEntity.ok(configRepo.save(def));
                });
    }

    @PutMapping
    public ResponseEntity<Configuracion> actualizar(@RequestBody Configuracion datos) {
        Configuracion conf = configRepo.findById(1L).orElse(new Configuracion());
        conf.setNombreEmpresa(datos.getNombreEmpresa());
        conf.setRuc(datos.getRuc());
        conf.setDireccion(datos.getDireccion());
        conf.setTelefono(datos.getTelefono());
        conf.setIvaPorcentaje(datos.getIvaPorcentaje());
        return ResponseEntity.ok(configRepo.save(conf));
    }
}
