package com.tarea.facturacion_api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DashboardData {
    private Double ventasTotales;
    private Integer usuariosConectados;
    private Double cpuUso;
    private List<Integer> ultimasVentas;
    
    // NUEVOS CAMPOS
    private Long totalClientes;
    private Long totalProductos;
}
