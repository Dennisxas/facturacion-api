package com.tarea.facturacion_api.controller;

import com.tarea.facturacion_api.model.Proveedor;
import com.tarea.facturacion_api.repository.ProveedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/proveedores")
public class ProveedorController {

    @Autowired
    private ProveedorRepository proveedorRepository;

    @GetMapping
    public List<Proveedor> listar() {
        return proveedorRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<Proveedor> crear(@RequestBody Proveedor proveedor) {
        return new ResponseEntity<>(proveedorRepository.save(proveedor), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Proveedor> actualizar(@PathVariable Long id, @RequestBody Proveedor datos) {
        return proveedorRepository.findById(id)
            .map(p -> {
                p.setRuc(datos.getRuc());
                p.setNombreEmpresa(datos.getNombreEmpresa());
                p.setContactoNombre(datos.getContactoNombre());
                p.setTelefono(datos.getTelefono());
                p.setEmail(datos.getEmail());
                p.setDireccion(datos.getDireccion());
                return new ResponseEntity<>(proveedorRepository.save(p), HttpStatus.OK);
            })
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (proveedorRepository.existsById(id)) {
            proveedorRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}