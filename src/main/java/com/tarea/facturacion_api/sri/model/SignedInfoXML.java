package com.tarea.facturacion_api.sri.model;

import jakarta.xml.bind.annotation.*;
import lombok.Data;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {"canonicalizationMethod", "signatureMethod", "reference"})
public class SignedInfoXML {
    @XmlElement(name = "CanonicalizationMethod", namespace = "http://www.w3.org/2000/09/xmldsig#")
    private AlgorithmXML canonicalizationMethod;
    
    @XmlElement(name = "SignatureMethod", namespace = "http://www.w3.org/2000/09/xmldsig#")
    private AlgorithmXML signatureMethod;
    
    @XmlElement(name = "Reference", namespace = "http://www.w3.org/2000/09/xmldsig#")
    private ReferenceXML reference;
}