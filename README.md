# Área Cliente de Seguros

Proyecto final del ciclo formativo de Desarrollo de Aplicaciones Multiplataforma (DAM).

Se trata de una aplicación cliente-servidor para la gestión de seguros de automóvil, compuesta por un backend desarrollado con Spring Boot y una aplicación móvil Android. El sistema permite a los clientes consultar y gestionar sus pólizas, recibos, documentos y datos personales desde una única aplicación.

## Funcionalidades principales

- Inicio de sesión de usuarios.
- Consulta y edición de datos personales (email, teléfono e IBAN).
- Visualización de pólizas contratadas.
- Gestión de conductores asociados a cada póliza.
- Consulta de recibos.
- Pago de recibos pendientes mediante PayPal Sandbox.
- Generación automática de documentos PDF.
- Descarga de documentos desde la aplicación móvil.

## Tecnologías utilizadas

### Backend
- Java 21
- Spring Boot
- Spring Data JPA
- Hibernate
- Maven
- OpenPDF

### Aplicación móvil
- Java
- Android Studio
- Retrofit
- WebView

### Base de datos
- MySQL

### Servicios externos
- PayPal Sandbox

## Estructura del proyecto

- `AreaCliente/` → Backend desarrollado con Spring Boot.
- `app/` → Aplicación móvil Android.
- `seguro_cliente.sql` → Script de creación e inserción de datos en la base de datos.

## Configuración

1. Clonar el repositorio:
2. Creaar la base de datos e importar el archivo seguro_cliente.sql.
3. Crear el archivo application.properties a partir de application-example.properties y configurar:
  spring.datasource.url=jdbc:mysql://localhost:3306/seguro_cliente
  spring.datasource.username=TU_USUARIO
  spring.datasource.password=TU_PASSWORD
  
  paypal.client-id=TU_CLIENT_ID
  paypal.client-secret=TU_CLIENT_SECRET
  paypal.base-url=https://api-m.sandbox.paypal.com
4. Ejecución del backend.

## Principales Endpoints REST

/usuarios
/login
/polizas
/conductores
/recibos
/pagos
/documentos

```bash
git clone <URL_DEL_REPOSITORIO>
cd AreaCliente
