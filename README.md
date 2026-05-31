# Sistema de Inventarios - Backend

Este proyecto es la interfaz de programación de aplicaciones (API) REST del Sistema de Inventarios, diseñada para gestionar de manera eficiente el almacenamiento, control y actualización de productos. La aplicación está construida sobre el ecosistema de Spring Boot y Java 21, ofreciendo una arquitectura robusta, escalable y mantenible.

El backend se conecta con una base de datos MySQL y proporciona los servicios necesarios para integrarse con aplicaciones frontend, como clientes desarrollados en Angular.

## Tecnologías Utilizadas

* **Lenguaje de Programación:** Java 21
* **Framework Principal:** Spring Boot (Spring Web MVC, Spring Data JPA)
* **Gestor de Dependencias:** Maven
* **Base de Datos:** MySQL
* **Mapeo Objeto-Relacional (ORM):** Hibernate (a través de Spring Data JPA)
* **Librerías de Utilidad:** Lombok (para la reducción de código repetitivo)
* **Registro de Sucesos (Logging):** SLF4J / Logback (integrado en Spring Boot)

## Características del Sistema

* **Arquitectura por Capas:** Separación limpia de responsabilidades mediante Controladores (REST API), Servicios (Lógica de Negocio), Repositorios (Acceso a Datos) y Modelos (Entidades de Persistencia).
* **Control CORS Integrado:** Configuración explícita para permitir solicitudes de recursos de origen cruzado desde el cliente Angular local (`http://localhost:4200`).
* **Manejo Personalizado de Excepciones:** Control centralizado de errores con respuestas HTTP adecuadas cuando un recurso no es encontrado.
* **Trazabilidad:** Sistema de logs personalizado para monitorear el flujo de datos y las operaciones de base de datos en consola.

## Estructura del Proyecto

El código fuente sigue las convenciones estándar de empaquetado de una aplicación Spring Boot:

```text
Backend/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── dc/
│   │   │       └── sistemadeinventarios/
│   │   │           ├── controller/
│   │   │           │   └── ProductoController.java
│   │   │           ├── exception/
│   │   │           │   └── RecursoNoEncontrado.java
│   │   │           ├── model/
│   │   │           │   └── Producto.java
│   │   │           ├── repository/
│   │   │           │   └── IProductoRepository.java
│   │   │           ├── service/
│   │   │           │   ├── IProductoService.java
│   │   │           │   └── ProductoService.java
│   │   │           └── SistemaDeInventariosApplication.java
│   │   └── resources/
│   │       ├── application.properties
│   │       └── application.properties.example
└── pom.xml
```

### Componentes Principales

* **Entidad `Producto` (`dc.sistemadeinventarios.model`):** Representa la tabla de productos en la base de datos MySQL. Contiene atributos como `idProducto`, `descripcion`, `precio` y `existencia`. Utiliza anotaciones de Lombok (`@Data`, `@Entity`, `@NoArgsConstructor`, `@AllArgsConstructor`) para generar automáticamente getters, setters, constructores y métodos estándar.
* **Controlador `ProductoController` (`dc.sistemadeinventarios.controller`):** Expone los puntos de acceso REST para el consumo externo y mapea las peticiones HTTP a las operaciones del servicio.
* **Servicio `ProductoService` (`dc.sistemadeinventarios.service`):** Contiene la lógica del negocio e implementa la interfaz `IProductoService`. Se comunica directamente con la capa de persistencia.
* **Repositorio `IProductoRepository` (`dc.sistemadeinventarios.repository`):** Interfaz que extiende de `JpaRepository` para proporcionar las operaciones CRUD básicas sin necesidad de implementar sentencias SQL manuales.
* **Excepción `RecursoNoEncontrado` (`dc.sistemadeinventarios.exception`):** Clase de excepción personalizada que devuelve un estado HTTP `404 Not Found` cuando se solicita un producto inexistente.

## Endpoints de la API

La API REST responde bajo la ruta base `/inventario-app/productos`. A continuación se detallan los endpoints disponibles:

| Método HTTP | Endpoint | Descripción | Cuerpo de Petición (Request Body) | Código de Respuesta |
| :--- | :--- | :--- | :--- | :--- |
| **GET** | `/inventario-app/productos` | Obtener la lista completa de productos | Ninguno | `200 OK` |
| **POST** | `/inventario-app/productos` | Registrar un nuevo producto en el sistema | JSON con `descripcion`, `precio` y `existencia` | `200 OK` |
| **GET** | `/inventario-app/productos/{id}` | Buscar un producto específico por su ID | Ninguno | `200 OK` / `404 Not Found` |
| **PUT** | `/inventario-app/productos/{id}` | Actualizar los datos de un producto existente | JSON con campos actualizados | `200 OK` / `404 Not Found` |
| **DELETE** | `/inventario-app/productos/{id}` | Eliminar un producto del inventario | Ninguno | `200 OK` (`{"eliminado": true}`) / `404 Not Found` |

### Ejemplo del Modelo JSON de Producto

```json
{
  "descripcion": "Teclado Mecánico RGB",
  "precio": 89.99,
  "existencia": 15
}
```

## Requisitos Previos

Para compilar y ejecutar el proyecto localmente, es necesario contar con los siguientes elementos instalados en el sistema:

* **Java Development Kit (JDK):** Versión 21 o superior.
* **Gestor de Base de Datos:** MySQL Server.
* **Gestor de Proyectos:** Maven (opcional, ya que se incluye el Maven Wrapper `mvnw`).

## Configuración y Despliegue

### 1. Configuración de la Base de Datos

Deberá contar con una base de datos MySQL en funcionamiento. Por defecto, la aplicación intentará crear la base de datos `inventario_db` si esta no existe. 

El archivo de configuración principal se encuentra en `src/main/resources/application.properties`. Asegúrese de ajustar las credenciales de conexión según su entorno:

```properties
spring.datasource.url=jdbc:mysql://localhost:3305/inventario_db?createDatabaseIfNotExist=true
spring.datasource.username=tu_usuario
spring.datasource.password=tu_contraseña
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

*Nota: Se incluye un archivo `application.properties.example` como plantilla.*

### 2. Ejecutar la Aplicación

Para iniciar el servidor de desarrollo, sitúese en la raíz del proyecto backend y ejecute el siguiente comando en la terminal:

**En sistemas Windows (PowerShell/CMD):**
```powershell
./mvnw.cmd spring-boot:run
```

**En sistemas basados en Unix (Linux/macOS):**
```bash
./mvnw spring-boot:run
```

Una vez ejecutado con éxito, la API estará disponible y escuchando peticiones en el puerto por defecto `http://localhost:8080`.

## Integración con Frontend

Este backend está diseñado para operar en conjunto con una aplicación frontend. El controlador `ProductoController` posee la configuración `@CrossOrigin(value = "http://localhost:4200")`, garantizando que el cliente de Angular pueda realizar peticiones HTTP de forma segura y sin problemas de políticas de origen cruzado en entornos de desarrollo local.
