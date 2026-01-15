package com.tarea.facturacion_api.sri.model;

import jakarta.xml.bind.annotation.*;
import lombok.Data;

@Data
@XmlRootElement(name = "factura")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {"infoTributaria", "infoFactura", "detalles", "signature"}) // <-- Agregado "signature"
public class FacturaXML {
    
    @XmlAttribute
    private String id = "comprobante";
    
    @XmlAttribute
    private String version = "1.0.0"; 

    private InfoTributariaXML infoTributaria;
    private InfoFacturaXML infoFactura;
    private DetallesXML detalles;
    
    @XmlElement(name = "Signature", namespace = "[http://www.w3.org/2000/09/xmldsig#](http://www.w3.org/2000/09/xmldsig#)")
    private SignatureXML signature; // <-- CAMPO NUEVO
}