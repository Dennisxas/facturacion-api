package com.tarea.facturacion_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling; // <--- IMPORTANTE

@SpringBootApplication
@EnableScheduling // <--- ESTA LÍNEA ES EL INTERRUPTOR QUE ENCIENDE EL ROBOT
public class FacturacionApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(FacturacionApiApplication.class, args);
	}

}