package com.tarea.facturacion_api.sri.model;

import jakarta.xml.bind.annotation.*;
import lombok.Data;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class CertXML {
    @XmlElement(name = "CertDigest", namespace = "http://uri.etsi.org/01903/v1.3.2#")
    private CertDigestXML certDigest;
    
    @XmlElement(name = "IssuerSerial", namespace = "http://uri.etsi.org/01903/v1.3.2#")
    private IssuerSerialXML issuerSerial;
}