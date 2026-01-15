package com.tarea.facturacion_api.sri.model;

import jakarta.xml.bind.annotation.*;
import lombok.Data;
import java.util.List;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {"transforms", "digestMethod", "digestValue"})
public class ReferenceXML {
    @XmlAttribute(name = "URI")
    private String uri = "#comprobante"; 
    
    @XmlElementWrapper(name = "Transforms", namespace = "http://www.w3.org/2000/09/xmldsig#")
    @XmlElement(name = "Transform", namespace = "http://www.w3.org/2000/09/xmldsig#")
    private List<AlgorithmXML> transforms;
    
    @XmlElement(name = "DigestMethod", namespace = "http://www.w3.org/2000/09/xmldsig#")
    private AlgorithmXML digestMethod;
    
    @XmlElement(name = "DigestValue", namespace = "http://www.w3.org/2000/09/xmldsig#")
    private String digestValue;
}