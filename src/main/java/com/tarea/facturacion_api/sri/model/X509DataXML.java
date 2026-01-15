package com.tarea.facturacion_api.sri.model;

import jakarta.xml.bind.annotation.*;
import lombok.Data;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class X509DataXML {
    @XmlElement(name = "X509Certificate", namespace = "http://www.w3.org/2000/09/xmldsig#")
    private String x509Certificate;
}