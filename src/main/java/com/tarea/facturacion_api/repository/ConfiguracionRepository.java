package com.tarea.facturacion_api.repository;

import com.tarea.facturacion_api.model.Configuracion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConfiguracionRepository extends JpaRepository<Configuracion, Long> {
}