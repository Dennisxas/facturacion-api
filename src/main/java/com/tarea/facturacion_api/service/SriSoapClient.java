package com.tarea.facturacion_api.service;

import org.springframework.stereotype.Service;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.time.Duration;

@Service
public class SriSoapClient {

    // URL LIMPIA
    private static final String SRI_URL = "https://celcer.sri.gob.ec/comprobantes-electronicos-ws/RecepcionComprobantesOffline?wsdl";

    public String enviarComprobante(String xmlFirmado) {
        try {
            String xmlBase64 = Base64.getEncoder().encodeToString(xmlFirmado.getBytes(StandardCharsets.UTF_8));

            String soapEnvelope = 
                "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ec=\"http://ec.gob.sri.ws.recepcion\">" +
                "   <soapenv:Header/>" +
                "   <soapenv:Body>" +
                "      <ec:validarComprobante>" +
                "         <xml>" + xmlBase64 + "</xml>" +
                "      </ec:validarComprobante>" +
                "   </soapenv:Body>" +
                "</soapenv:Envelope>";

            HttpClient client = HttpClient.newBuilder()
                    .connectTimeout(Duration.ofSeconds(20)) // Aumentamos timeout por si acaso
                    .build();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(SRI_URL))
                    .header("Content-Type", "text/xml; charset=utf-8")
                    .POST(HttpRequest.BodyPublishers.ofString(soapEnvelope))
                    .build();

            System.out.println(">>> [SRI-CLIENT] Enviando a: " + SRI_URL);
            
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            System.out.println(">>> [SRI-CLIENT] Respuesta HTTP: " + response.statusCode());
            
            // --- NUEVO: IMPRIMIR LO QUE DIJO EL SRI ---
            System.out.println(">>> [SRI-RESPONSE]: " + response.body());
            // ------------------------------------------

            return response.body();

        } catch (Exception e) {
            e.printStackTrace();
            return "ERROR_CONEXION: " + e.getMessage();
        }
    }
}