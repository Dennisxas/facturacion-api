package com.tarea.facturacion_api.repository;

import com.tarea.facturacion_api.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    // Método para buscar usuario por su nombre de login
    Optional<Usuario> findByUsername(String username);
}