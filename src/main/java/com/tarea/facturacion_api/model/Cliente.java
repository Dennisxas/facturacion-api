package com.tarea.facturacion_api.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "clientes")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Nuevo campo obligatorio y único
    @Column(length = 10, unique = true, nullable = false)
    private String cedula;

    private String nombre;
    private String apellido;
    private String direccion;
    private String email;
}