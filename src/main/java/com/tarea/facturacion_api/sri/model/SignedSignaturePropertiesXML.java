package com.tarea.facturacion_api.sri.model;

import jakarta.xml.bind.annotation.*;
import lombok.Data;
import java.util.Date;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class SignedSignaturePropertiesXML {
    @XmlElement(name = "SigningTime", namespace = "http://uri.etsi.org/01903/v1.3.2#")
    private Date signingTime;
    
    @XmlElement(name = "SigningCertificate", namespace = "http://uri.etsi.org/01903/v1.3.2#")
    private SigningCertificateXML signingCertificate;
}