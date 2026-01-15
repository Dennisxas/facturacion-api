package com.tarea.facturacion_api.model;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "facturas")
public class Factura {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime fecha;
    private Double total;

    // --- NUEVOS CAMPOS PARA EL SRI (Preparación) ---
    
    // La Clave de Acceso es el "ID único" de 49 dígitos que pide el SRI
    @Column(length = 49, unique = true)
    private String claveAcceso;

    // Estado: "PENDIENTE", "ENVIADO", "AUTORIZADO", "RECHAZADO"
    private String estadoSri;

    // Fecha en que el SRI nos dio el visto bueno
    private LocalDateTime fechaAutorizacion;

    // Guardaremos el XML firmado en la base de datos por si necesitamos reimprimirlo
    @Lob // Large Object (para textos largos)
    @Column(columnDefinition = "TEXT")
    private String xmlContenido;

    // --- RELACIONES (Igual que antes) ---

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    @OneToMany(mappedBy = "factura", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<DetalleFactura> detalles;
}