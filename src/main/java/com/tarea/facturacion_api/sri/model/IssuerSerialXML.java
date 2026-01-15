package com.tarea.facturacion_api.sri.model;

import jakarta.xml.bind.annotation.*;
import lombok.Data;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class IssuerSerialXML {
    @XmlElement(name = "X509IssuerName", namespace = "http://www.w3.org/2000/09/xmldsig#")
    private String x509IssuerName;
    
    @XmlElement(name = "X509SerialNumber", namespace = "http://www.w3.org/2000/09/xmldsig#")
    private String x509SerialNumber;
}