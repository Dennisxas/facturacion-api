package com.tarea.facturacion_api.service;

import com.tarea.facturacion_api.model.Factura;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class WhatsappService {

    @Value("${twilio.account.sid}")
    private String accountSid;

    @Value("${twilio.auth.token}")
    private String authToken;

    @Value("${twilio.phone.number}")
    private String fromPhoneNumber;

    public void enviarNotificacion(Factura factura) {
        try {
            Twilio.init(accountSid, authToken);

            // Mensaje personalizado
            String mensajeTexto = "Hola " + factura.getCliente().getNombre() + 
                                  ", tu factura #" + factura.getId() + 
                                  " por un total de $" + factura.getTotal() + 
                                  " ha sido generada exitosamente.";

            // IMPORTANTE: En modo prueba (Sandbox), solo puedes enviar mensajes a números verificados.
            // Para este ejemplo, pondremos tu número verificado aquí.
            // Reemplaza '+593...' con TU número de celular real (código de país incluido).
            String miCelular = "+593978958721"; 

            Message message = Message.creator(
                    new PhoneNumber("whatsapp:" + miCelular), // Destino
                    new PhoneNumber("whatsapp:" + fromPhoneNumber), // Origen (Twilio)
                    mensajeTexto)
                    .create();

            System.out.println("Mensaje enviado SID: " + message.getSid());

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error al enviar WhatsApp");
        }
    }
}