# gym-booking-microservices
Proyecto de portfolio de una aplicación de reserva de clases de gimnasio con arquitectura de microservicios en Java y Spring Boot.

## Arquitectura
El sistema está compuesto por varios microservicios, cada uno con una responsabilidad única. La comunicación se gestiona a través de un API Gateway y un Service Discovery para un sistema desacoplado y escalable.



### Microservicios
* **class-catalog-service (Puerto 8082):** Gestiona los tipos de clases y sus descripciones.
* **member-service (Próximamente):** Gestiona el registro y los perfiles de los socios.
* **timetable-service (Próximamente):** Gestionará el horario y el aforo de las clases.
* **booking-service (Próximamente):** Orquestará el proceso de reserva.
* **api-gateway (Próximamente):** Punto de entrada único al sistema.
* **discovery-service (Próximamente):** Registro y descubrimiento de servicios (Eureka).

## Tecnologías Utilizadas
* **Lenguaje:** Java 17
* **Framework:** Spring Boot 3
* **Base de Datos:** H2 (en memoria para desarrollo)
* **Gestión de Proyecto:** Maven
* **API:** RESTful con Spring Web
* **Documentación API:** SpringDoc OpenAPI (Swagger)
* **Herramientas:** Lombok

## Cómo Empezar

Para ejecutar este proyecto, necesitarás tener instalado Java 17 y Maven.

1.  **Clonar el repositorio:**
    ```bash
    git clone [https://github.com/TU_USUARIO/gym-booking-microservices.git](https://github.com/TU_USUARIO/gym-booking-microservices.git)
    cd gym-booking-microservices
    ```

2.  **Construir el proyecto padre:**
    Este paso es crucial para que los módulos hijos puedan encontrar sus dependencias.
    ```bash
    cd gym-booking-parent
    mvn clean install
    ```

3.  **Ejecutar los microservicios:**
    Abre un terminal para cada microservicio que quieras ejecutar y arráncalo con el siguiente comando:
    ```bash
    # Desde la carpeta de cada servicio (ej: class-catalog-service)
    mvn spring-boot:run
    ```

4.  **Acceder a la API:**
    * **Catálogo de Clases:** `http://localhost:8082/swagger-ui/index.html`
    * **Servicio de Miembros:** `http://localhost:8081/swagger-ui/index.html`

## Estado del Proyecto (26/09/2025)
Actualmente, el servicio `class-catalog-service` está funcional con operaciones CRUD básicas y manejo de errores centralizado. El resto de los componentes de la arquitectura están planificados y se implementarán a continuación.
