# 🚀 Clean Architecture Reactive Spring Boot Reminder App

Este proyecto es una implementación de ejemplo de una aplicación de gestión de recordatorios, construida utilizando **Clean Architecture** y **Programación Reactiva** con **Spring Boot WebFlux**. Su objetivo es demostrar cómo se pueden aplicar los principios de Clean Architecture junto con un paradigma de desarrollo reactivo para crear una aplicación escalable, mantenible y desacoplada.

## 🌟 Características

* **Gestión de Recordatorios:**
    * Crear nuevos recordatorios.
    * Consultar recordatorios por ID.
    * Eliminar recordatorios.
* **Reglas de Negocio:** Validación de planes de suscripción para la creación de recordatorios (ej. "premium" o "basic" permitidos).
* **Arquitectura Limpia:** Separación clara de responsabilidades en capas (Dominio, Aplicación, Infraestructura, Presentación).
* **Programación Reactiva:** Uso de Spring WebFlux y Project Reactor para un manejo de solicitudes no bloqueante y eficiente de los recursos.

## 🏗️ Arquitectura del Proyecto

Este proyecto sigue los principios de **Clean Architecture** (también conocida como Hexagonal Architecture o Ports and Adapters). El objetivo principal es mantener la lógica de negocio central (Dominio y Aplicación) independiente de los detalles técnicos como la base de datos o el framework web.

Las capas se organizan de la siguiente manera, con las dependencias apuntando **hacia el interior**:

1.  **Domain Layer (Capa de Dominio):**
    * Contiene las entidades de negocio (ej. `Reminder`) y las reglas de negocio core.
    * Define las interfaces (puertos) para las operaciones de salida (ej. `ReminderRepository`), que serán implementadas por la capa de Infraestructura.
    * **No tiene dependencias de otras capas del proyecto.**

2.  **Application Layer (Capa de Aplicación):**
    * Contiene los "Casos de Uso" (Use Cases) o "Interactores" (ej. `CreateReminderUseCase`, `GetReminderUseCase`).
    * Orquesta la lógica de negocio específica de la aplicación utilizando las entidades del Dominio y las interfaces de los puertos.
    * Depende únicamente de la Capa de Dominio.

3.  **Infrastructure Layer (Capa de Infraestructura):**
    * Proporciona las implementaciones concretas de las interfaces (puertos) definidas en la Capa de Dominio (ej. `InMemoryReminderRepository` o una implementación de base de datos real).
    * Maneja detalles técnicos como la persistencia de datos, la comunicación con servicios externos, etc.
    * Depende de la Capa de Dominio.

4.  **Presentation Layer (Capa de Presentación):**
    * Es la interfaz de usuario o la API que interactúa con el mundo exterior (ej. `ReminderController` como API REST).
    * Traduce las solicitudes externas a llamadas a los Casos de Uso de la Capa de Aplicación y formatea las respuestas.
    * Contiene DTOs (Data Transfer Objects) específicos de la API.
    * Depende de la Capa de Aplicación.

### Programación Reactiva con Spring WebFlux

La aplicación utiliza **Spring WebFlux** y **Project Reactor** para construir un sistema de procesamiento de solicitudes completamente no bloqueante y basado en eventos. Esto permite una mayor escalabilidad y eficiencia en el uso de recursos, especialmente en aplicaciones con muchas operaciones de I/O concurrentes. Los `Mono` y `Flux` de Reactor son utilizados en las firmas de los métodos a través de las capas para manejar los flujos de datos asíncronos.

## 🛠️ Tecnologías Utilizadas

* **Java**: Lenguaje de programación.
* **Spring Boot 3.x**: Framework para crear aplicaciones Spring de forma rápida.
* **Spring WebFlux**: Módulo de Spring para construir aplicaciones web reactivas y no bloqueantes.
* **Project Reactor**: Implementación de las especificaciones Reactive Streams, base de Spring WebFlux.
* **Gradle**: Herramienta de automatización de construcción.

## 🚀 Cómo Empezar

### Prerequisitos

* Java Development Kit (JDK) 17 o superior.
* Gradle (opcional, Spring Boot incluye un wrapper).

### Clonar el Repositorio

```bash
git clone [https://github.com/tu-usuario/nombre-del-repositorio.git](https://github.com/tu-usuario/nombre-del-repositorio.git)
cd nombre-del-repositorio
