package com.tarea.facturacion_api.sri.model;

import jakarta.xml.bind.annotation.*;
import lombok.Data;
import java.util.List;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {
    "fechaEmision", "dirEstablecimiento", "obligadoContabilidad", 
    "tipoIdentificacionComprador", "razonSocialComprador", "identificacionComprador", 
    "totalSinImpuestos", "totalDescuento", "totalConImpuestos", 
    "propina", "importeTotal", "moneda", "pagos"
})
public class InfoFacturaXML {
    private String fechaEmision;
    private String dirEstablecimiento;
    private String obligadoContabilidad;
    private String tipoIdentificacionComprador;
    private String razonSocialComprador;
    private String identificacionComprador;
    private String totalSinImpuestos;
    private String totalDescuento;
    
    @XmlElementWrapper(name = "totalConImpuestos")
    @XmlElement(name = "totalImpuesto")
    private List<TotalImpuestoXML> totalConImpuestos;
    
    private String propina;
    private String importeTotal;
    private String moneda;
    
    @XmlElementWrapper(name = "pagos")
    @XmlElement(name = "pago")
    private List<PagoXML> pagos;
}