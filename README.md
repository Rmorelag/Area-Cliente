# Área Cliente
Backend desarrollado con Spring Boot para la gestión de clientes de seguros. Permite administrar usuarios, pólizas, recibos y documentos asociados mediante una API REST.

## Tecnologías
* Java
* Spring Boot
* Maven
* JPA / Hibernate
* MySQL

## Configuración
1.Clonar el repositorio:
git clone <URL_DEL_REPO>
cd AreaCliente

2.Crear la base de datos y ejecutar:
seguro_cliente.sql

3.Configurar `application.properties`:

spring.datasource.url=jdbc:mysql://localhost:3306/seguros
spring.datasource.username=usuario
spring.datasource.password=contraseña

## Ejecución
mvn spring-boot:run

## Endpoints

* `/usuarios`
* `/polizas`
* `/recibos`
* `/documentos`



