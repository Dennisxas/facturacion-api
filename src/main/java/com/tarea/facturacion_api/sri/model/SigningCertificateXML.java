package com.tarea.facturacion_api.sri.model;

import jakarta.xml.bind.annotation.*;
import lombok.Data;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class SigningCertificateXML {
    @XmlElement(name = "Cert", namespace = "http://uri.etsi.org/01903/v1.3.2#")
    private CertXML cert;
}