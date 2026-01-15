package com.tarea.facturacion_api.sri.model;

import jakarta.xml.bind.annotation.*;
import lombok.Data;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {"signedInfo", "signatureValue", "keyInfo", "object"})
public class SignatureXML {
    @XmlAttribute(name = "Id")
    private String id = "Signature-1";
    
    @XmlElement(name = "SignedInfo", namespace = "http://www.w3.org/2000/09/xmldsig#")
    private SignedInfoXML signedInfo;
    
    @XmlElement(name = "SignatureValue", namespace = "http://www.w3.org/2000/09/xmldsig#")
    private String signatureValue;
    
    @XmlElement(name = "KeyInfo", namespace = "http://www.w3.org/2000/09/xmldsig#")
    private KeyInfoXML keyInfo;
    
    @XmlElement(name = "Object", namespace = "http://www.w3.org/2000/09/xmldsig#")
    private ObjectXML object;
}