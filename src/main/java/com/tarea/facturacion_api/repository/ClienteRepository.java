package com.tarea.facturacion_api.repository;

import com.tarea.facturacion_api.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    // JpaRepository<Entidad, TipoDeID>
    // ¡Eso es todo! Spring nos da findAll, findById, save, delete, etc.
}