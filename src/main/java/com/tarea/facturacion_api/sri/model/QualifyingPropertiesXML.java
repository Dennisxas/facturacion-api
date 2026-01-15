package com.tarea.facturacion_api.sri.model;

import jakarta.xml.bind.annotation.*;
import lombok.Data;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class QualifyingPropertiesXML {
    @XmlAttribute(name = "Target")
    private String target = "#Signature-1";
    
    @XmlElement(name = "SignedProperties", namespace = "http://uri.etsi.org/01903/v1.3.2#")
    private SignedPropertiesXML signedProperties;
}