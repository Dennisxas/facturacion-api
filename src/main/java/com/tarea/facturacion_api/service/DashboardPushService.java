package com.tarea.facturacion_api.service;

import com.tarea.facturacion_api.dto.DashboardData;
import com.tarea.facturacion_api.model.Factura;
import com.tarea.facturacion_api.repository.ClienteRepository; // Importar
import com.tarea.facturacion_api.repository.FacturaRepository;
import com.tarea.facturacion_api.repository.ProductoRepository;
import com.tarea.facturacion_api.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class DashboardPushService {

    @Autowired
    private SimpMessagingTemplate template;

    @Autowired
    private FacturaRepository facturaRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private ProductoRepository productoRepository;
    @Autowired
    private ClienteRepository clienteRepository; // Inyectar ClienteRepo

    @Scheduled(fixedRate = 3000)
    public void enviarDatosDashboard() {
        
        // 1. OBTENER DATOS REALES
        long totalUsuarios = usuarioRepository.count();
        long totalProductos = productoRepository.count();
        long totalClientes = clienteRepository.count(); // Contar clientes reales
        
        List<Factura> facturas = facturaRepository.findAll();
        double totalVendido = facturas.stream().mapToDouble(Factura::getTotal).sum();

        // 2. DATOS SIMULADOS (CPU y Gráfica)
        Random rand = new Random();
        double cpu = 1 + (2 * rand.nextDouble()); //--> Simular uso CPU entre 1% y 3%
        
        List<Integer> grafica = new ArrayList<>();
        for (int i = 0; i < 7; i++) grafica.add(rand.nextInt(100)); //--> Simular datos gráfica


        // 3. Empaquetar todo (Asegúrate de respetar el orden del constructor de DashboardData)
        DashboardData data = new DashboardData(
            totalVendido, 
            (int) totalUsuarios, 
            cpu, 
            grafica, 
            totalClientes, 
            totalProductos
        );

        // 4. Enviar
        template.convertAndSend("/topic/dashboard", data);
    }
}
