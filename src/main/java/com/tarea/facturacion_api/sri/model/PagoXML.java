package com.tarea.facturacion_api.sri.model;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {"formaPago", "total", "plazo", "unidadTiempo"})
public class PagoXML {
    private String formaPago;
    private String total;
    private String plazo;
    private String unidadTiempo;
}