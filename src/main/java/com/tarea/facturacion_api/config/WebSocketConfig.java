package com.tarea.facturacion_api.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        // Habilitamos un broker simple en memoria para enviar mensajes a los clientes
        config.enableSimpleBroker("/topic");
        config.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // Este es el punto de conexión (Endpoint) donde Angular se conectará
        // setAllowedOriginPatterns("*") es vital para evitar errores de CORS
        registry.addEndpoint("/ws-dashboard")
                .setAllowedOriginPatterns("*")
                .withSockJS(); // Habilita compatibilidad
    }
}
