# Arquitectura de Microservicios - Examen Final

Este repositorio contiene un ecosistema de **10 microservicios** desarrollado bajo un enfoque modular, escalable y robusto. El objetivo del proyecto es implementar comunicación inter-servicio eficiente, documentación estandarizada, navegación dinámica y pruebas unitarias de alta cobertura.

---

## Tecnologías Utilizadas

* **Java 17** & **Spring Boot**
* **Spring Cloud OpenFeign** (Comunicación síncrona declarativa)
* **Spring HATEOAS** (Navegación basada en hipermedios)
* **Springdoc OpenAPI / Swagger UI** (Documentación de API)
* **JUnit 5 & Mockito** (Pruebas unitarias y aislamiento de componentes)

---

## Estructura del Ecosistema

El proyecto se divide en los siguientes microservicios orientados al dominio:

* **`ms-usuario`**: Gestión de perfiles y roles de usuario (Implementa HATEOAS).
* **`ms-pedidos`**: Orquestación y flujos de órdenes de compra (Integración con Swagger).
* **`ms-productos`**: Catálogo de ítems y stock disponible.
* **`ms-pagos`**: Procesamiento de transacciones financieras.
* **`ms-inventario`**: Control y auditoría de almacenes.
* **`ms-envios`**: Logística y tracking de despachos (Client Feign implementado).
* **`ms-proveedores`**: Gestión de cadenas de suministro (Client Feign implementado).
* **`ms-reportes`**: Motor analítico de datos (Client Feign implementado).
* **`ms-sucursales`**: Control de sedes físicas (Client Feign implementado).
* **`ms-empleados`**: Administración de personal interno.

---

## Componentes Clave

### Comunicación Inter-Servicios (OpenFeign)
Se reemplazaron las llamadas tradicionales por clientes declarativos **OpenFeign** para simplificar el consumo de endpoints entre microservicios (por ejemplo, `ms-pedidos` comunicándose de forma fluida con `ms-productos` y `ms-pagos`), reduciendo el acoplamiento y mejorando la legibilidad.

### Navegación Dinámica (HATEOAS)
Implementación del principio **HATEOAS** (*Hypermedia As The Engine Of Application State*) en recursos críticos como `ms-usuario`. Las respuestas ahora guían dinámicamente al cliente incluyendo enlaces (`links`) a las acciones permitidas según el estado actual del recurso.

### Documentación Interactiva (Swagger)
Cada microservicio cuenta con su propia interfaz gráfica de **Swagger UI** expuesta para facilitar las pruebas del equipo de desarrollo y QA.
* **Ruta local por defecto:** `http://localhost:[PUERTO]/swagger-ui.html`

### Calidad de Código (Pruebas Unitarias)
Robustez garantizada mediante un set de pruebas ejecutadas con **JUnit 5** y **Mockito** para simular las capas de persistencia y servicios externos.
* `ms-pagos`: 5 pruebas críticas superadas.
* `ms-productos`: 7 pruebas críticas superadas.
* `ms-pedidos`: 7 pruebas críticas superadas.
* # Arquitectura de Microservicios - Examen Final

Este repositorio contiene un ecosistema de **10 microservicios** desarrollado bajo un enfoque modular, escalable y robusto. El objetivo del proyecto es implementar comunicación inter-servicio eficiente, documentación estandarizada, navegación dinámica y pruebas unitarias de alta cobertura.

---

## Tecnologías Utilizadas

* **Java 17** & **Spring Boot**
* **Spring Cloud OpenFeign** (Comunicación síncrona declarativa)
* **Spring HATEOAS** (Navegación basada en hipermedios)
* **Springdoc OpenAPI / Swagger UI** (Documentación de API)
* **JUnit 5 & Mockito** (Pruebas unitarias y aislamiento de componentes)

---

## Estructura del Ecosistema

El proyecto se divide en los siguientes microservicios orientados al dominio:

* **`ms-usuario`**: Gestión de perfiles y roles de usuario (Implementa HATEOAS).
* **`ms-pedidos`**: Orquestación y flujos de órdenes de compra (Integración con Swagger).
* **`ms-productos`**: Catálogo de ítems y stock disponible.
* **`ms-pagos`**: Procesamiento de transacciones financieras.
* **`ms-inventario`**: Control y auditoría de almacenes.
* **`ms-envios`**: Logística y tracking de despachos (Client Feign implementado).
* **`ms-proveedores`**: Gestión de cadenas de suministro (Client Feign implementado).
* **`ms-reportes`**: Motor analítico de datos (Client Feign implementado).
* **`ms-sucursales`**: Control de sedes físicas (Client Feign implementado).
* **`ms-empleados`**: Administración de personal interno.

---

## Componentes Clave

### Comunicación Inter-Servicios (OpenFeign)
Se reemplazaron las llamadas tradicionales por clientes declarativos **OpenFeign** para simplificar el consumo de endpoints entre microservicios (por ejemplo, `ms-pedidos` comunicándose de forma fluida con `ms-productos` y `ms-pagos`), reduciendo el acoplamiento y mejorando la legibilidad.

### Navegación Dinámica (HATEOAS)
Implementación del principio **HATEOAS** (*Hypermedia As The Engine Of Application State*) en recursos críticos como `ms-usuario`. Las respuestas ahora guían dinámicamente al cliente incluyendo enlaces (`links`) a las acciones permitidas según el estado actual del recurso.

### Documentación Interactiva (Swagger)
Cada microservicio cuenta con su propia interfaz gráfica de **Swagger UI** expuesta para facilitar las pruebas del equipo de desarrollo y QA.
* **Ruta local por defecto:** `http://localhost:[PUERTO]/swagger-ui.html`

### Calidad de Código (Pruebas Unitarias)
Robustez garantizada mediante un set de pruebas ejecutadas con **JUnit 5** y **Mockito** para simular las capas de persistencia y servicios externos.
* `ms-pagos`: 5 pruebas críticas superadas.
* `ms-productos`: 7 pruebas críticas superadas.
* `ms-pedidos`: 7 pruebas críticas superadas.
* `ms-inventario`: 4 pruebas críticas superadas.
* `ms-usuarios`: # pruebas críticas superadas.
---

## Instalación y Ejecución Local

1. **Clonar el repositorio:**
   ```bash
   git clone [https://github.com/paofranco-a11y/prueba.git](https://github.com/paofranco-a11y/prueba.git)
   cd exament-test

---

## Instalación y Ejecución Local

1. **Clonar el repositorio:**
   ```bash
   git clone [https://github.com/paofranco-a11y/prueba.git](https://github.com/paofranco-a11y/prueba.git)
   cd exament-test

---

## Instalación y Ejecución Local

1. **Clonar el repositorio:**
   ```bash
   git clone [https://github.com/paofranco-a11y/prueba.git](https://github.com/paofranco-a11y/prueba.git)
   cd exament-test
