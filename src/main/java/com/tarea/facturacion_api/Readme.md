##*************************************************************************
Sistema de Facturación Electrónica (SRI & Roles)

API REST completa desarrollada en Spring Boot que implementa facturación con simulación de SRI, gestión de usuarios por roles (RBAC), seguridad JWT y reportes PDF.

 Funcionalidades Principales

Facturación Electrónica (SRI):

Simulación de autorización en tiempo real.

Generación de Clave de Acceso (49 dígitos).

Generación de XML estándar firmado digitalmente (Simulación con archivo .p12 real).

Seguridad Avanzada:

Autenticación JWT Stateless.

Roles diferenciados: ADMIN, VENDEDOR, CONTADOR.

Reportes Gerenciales:

Descarga de listados de Clientes y Productos en PDF.

##******************************************************************
Roles y Credenciales

Rol             Usuario         Clave     Permisos    |

ADMIN           admin           123      Control Total (Usuarios, Reportes, SRI)           

VENDEDOR        vendedor1       123      Operativo (Facturar, Clientes)

CONTADOR        contador1       123      Auditoría (Reportes, SRI)


Guía de Endpoints (Ejemplos)

1. Simulación SRI (Contador/Admin)

Endpoint: POST /sri/autorizar/{idFactura}

Descripción: Envía la factura al simulador del SRI.

Respuesta Exitosa:
{
  "estadoSri": "AUTORIZADO",
  "claveAcceso": "16083872811362137...",
  "xmlContenido": "<?xml ...>"
}

2. Reportes PDF

Clientes: GET /reportes/clientes/pdf

Productos: GET /reportes/productos/pdf

Nota: Usar "Send and Download" en Postman.

3. Gestión de Usuarios (Solo Admin)

Crear Usuario: POST /usuarios

Body: {"username": "nuevo", "password": "123", "role": "VENDEDOR"}

Ejecución Local

Configurar base de datos MySQL en application.properties.

Colocar archivo de firma firma.p12 en src/main/resources.

Ejecutar: ./mvnw spring-boot:run

Documentación Swagger: http://localhost:8080/swagger-ui/index.html