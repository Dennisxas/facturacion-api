package com.tarea.facturacion_api.sri.model;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import lombok.Data;
import java.util.List;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class DetallesXML {
    @XmlElement(name = "detalle")
    private List<DetalleXML> detalle;
}