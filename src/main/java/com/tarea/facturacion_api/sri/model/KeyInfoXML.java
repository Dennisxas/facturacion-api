package com.tarea.facturacion_api.sri.model;

import jakarta.xml.bind.annotation.*;
import lombok.Data;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class KeyInfoXML {
    @XmlElement(name = "X509Data", namespace = "http://www.w3.org/2000/09/xmldsig#")
    private X509DataXML x509Data;
}