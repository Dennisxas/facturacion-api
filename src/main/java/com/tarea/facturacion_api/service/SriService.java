package com.tarea.facturacion_api.service;

import com.tarea.facturacion_api.model.DetalleFactura;
import com.tarea.facturacion_api.model.Factura;
import com.tarea.facturacion_api.repository.FacturaRepository;
import com.tarea.facturacion_api.sri.model.*;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Marshaller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.StringWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class SriService {

    @Autowired private FacturaRepository facturaRepository;
    @Autowired private WhatsappService whatsappService;
    @Autowired private FirmaService firmaService;
    @Autowired private SriSoapClient sriSoapClient; // <--- NUEVO CLIENTE

    public Factura procesarFacturaElectronica(Long idFactura) {
        System.out.println(">>> [SRI] Iniciando proceso REAL para factura ID: " + idFactura);
        
        Factura factura = facturaRepository.findById(idFactura)
                .orElseThrow(() -> new RuntimeException("Factura no encontrada"));

        try {
            // 1. Datos y Clave
            String claveAcceso = generarClaveAccesoSimulada();
            factura.setClaveAcceso(claveAcceso);

            // 2. XML Base
            String xml = generarXmlJaxb(factura, claveAcceso);
            
            // 3. Firma XAdES (Real)
            String firmaDigital = firmaService.firmarDatos(xml);
            // Inyección simulada de la firma para no romper el XML
            // (El SRI real validaría la firma interna, pero para el ejercicio enviamos el XML base firmado)
            // Para envío real, deberíamos usar librerías de firma XMLDSig completas.
            // Aquí enviamos el XML generado con el comentario de firma para probar conexión.
            String xmlParaEnviar = xml + "\n<!-- FIRMA: " + firmaDigital + " -->";
            
            factura.setXmlContenido(xmlParaEnviar);

            // 4. ENVÍO REAL AL WEB SERVICE (Adiós Simulación)
            String respuestaSri = sriSoapClient.enviarComprobante(xmlParaEnviar);
            
            // 5. Analizar Respuesta (Parsing básico)
            if (respuestaSri.contains("RECIBIDA") || respuestaSri.contains("AUTORIZADO")) {
                factura.setEstadoSri("ENVIADO_SRI"); // Cambiamos estado
                factura.setFechaAutorizacion(LocalDateTime.now());
                System.out.println(">>> [SRI] ¡Documento recibido por el SRI!");
                whatsappService.enviarNotificacion(factura);
            } else if (respuestaSri.contains("DEVUELTA")) {
                factura.setEstadoSri("DEVUELTA");
                System.err.println(">>> [SRI] Documento devuelto por errores.");
            } else {
                // Si la respuesta es extraña o error de conexión
                factura.setEstadoSri("ERROR_ENVIO");
                System.err.println(">>> [SRI] Respuesta desconocida: " + respuestaSri);
            }

        } catch (Exception e) {
            System.err.println(">>> [SRI] Error crítico: " + e.getMessage());
            e.printStackTrace();
            factura.setEstadoSri("ERROR_INTERNO");
        }

        return facturaRepository.save(factura);
    }

    // --- MÉTODOS PRIVADOS (Se mantienen igual que antes) ---
    // (Copia aquí los métodos generarXmlJaxb, format y generarClaveAccesoSimulada del archivo anterior)
    // OJO: Asegúrate de NO borrarlos. Si copias y pegas todo, asegúrate de incluir estos métodos auxiliares abajo.
    
    private String generarXmlJaxb(Factura f, String claveAcceso) throws Exception {
        FacturaXML xml = new FacturaXML();
        
        InfoTributariaXML infoT = new InfoTributariaXML();
        infoT.setAmbiente("1");
        infoT.setTipoEmision("1");
        infoT.setRazonSocial("MI EMPRESA S.A.");
        infoT.setNombreComercial("MI TIENDA");
        infoT.setRuc("1799999999001");
        infoT.setClaveAcceso(claveAcceso);
        infoT.setCodDoc("01");
        infoT.setEstab("001");
        infoT.setPtoEmi("001");
        infoT.setSecuencial(String.format("%09d", f.getId()));
        infoT.setDirMatriz("Av. Principal 123");
        xml.setInfoTributaria(infoT);

        InfoFacturaXML infoF = new InfoFacturaXML();
        infoF.setFechaEmision(new SimpleDateFormat("dd/MM/yyyy").format(new Date()));
        infoF.setDirEstablecimiento("Av. Sucursal");
        infoF.setObligadoContabilidad("NO");
        infoF.setTipoIdentificacionComprador("05");
        infoF.setRazonSocialComprador(f.getCliente().getNombre() + " " + f.getCliente().getApellido());
        String ident = f.getCliente().getCedula() != null ? f.getCliente().getCedula() : "9999999999";
        infoF.setIdentificacionComprador(ident);
        infoF.setTotalSinImpuestos(format(f.getTotal())); 
        infoF.setTotalDescuento("0.00");
        infoF.setTotalConImpuestos(Arrays.asList(new TotalImpuestoXML("2", "4", format(f.getTotal()), "0.00")));
        infoF.setPropina("0.00");
        infoF.setImporteTotal(format(f.getTotal()));
        infoF.setMoneda("DOLAR");
        infoF.setPagos(Arrays.asList(new PagoXML("01", format(f.getTotal()), "0", "DIAS")));
        xml.setInfoFactura(infoF);

        DetallesXML detallesContainer = new DetallesXML();
        List<DetalleXML> listaDetalles = new ArrayList<>();
        for (DetalleFactura det : f.getDetalles()) {
            DetalleXML d = new DetalleXML();
            d.setCodigoPrincipal(det.getProducto().getId().toString());
            d.setDescripcion(det.getProducto().getNombre());
            d.setCantidad(format(det.getCantidad()));
            d.setPrecioUnitario(format(det.getPrecioUnitario()));
            d.setDescuento("0.00");
            d.setPrecioTotalSinImpuesto(format(det.getCantidad() * det.getPrecioUnitario()));
            d.setImpuestos(Arrays.asList(new ImpuestoXML("2", "4", "15", format(det.getCantidad() * det.getPrecioUnitario()), "0.00")));
            listaDetalles.add(d);
        }
        detallesContainer.setDetalle(listaDetalles);
        xml.setDetalles(detallesContainer);

        JAXBContext context = JAXBContext.newInstance(FacturaXML.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        StringWriter writer = new StringWriter();
        marshaller.marshal(xml, writer);
        return writer.toString();
    }

    private String format(Number n) {
        return new BigDecimal(n.toString()).setScale(2, RoundingMode.HALF_UP).toString();
    }

    private String generarClaveAccesoSimulada() {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 49; i++) sb.append(random.nextInt(10));
        return sb.toString();
    }
}