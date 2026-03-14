# Student Management API

API REST desarrollada con **Spring Boot** para gestionar alumnos, cursos, asignaturas y notas.

El sistema permite:

- registrar estudiantes
- asignarlos a cursos
- registrar calificaciones
- calcular promedios
- generar informes académicos

Proyecto desarrollado como **ejercicio de backend para demostrar arquitectura por capas, manejo de relaciones JPA y lógica de negocio**.

---

# Stack Tecnológico

- Java 21
- Spring Boot
- Spring Web MVC
- Spring Data JPA
- Hibernate
- MySQL
- Maven

---

# Arquitectura

La API sigue una **arquitectura por capas**.

```text
Client
  |
  v
Controller
  |
  v
Service
  |
  v
Repository
  |
  v
MySQL
```

Responsabilidades:

- **Controller** → expone endpoints REST
- **Service** → contiene la lógica de negocio
- **Repository** → acceso a datos con JPA
- **Database** → persistencia en MySQL

---

# Modelo de dominio

```text
Curso
└── Alumno
    └── Nota
        └── Asignatura
```

Relaciones principales:

- un **curso tiene muchos alumnos**
- un **alumno tiene muchas notas**
- cada **nota pertenece a una asignatura**

---

# Quick Start

## Clonar repositorio

```bash
git clone https://github.com/juandev201305/gestion-alumnos-backend
cd gestion-alumnos-backend
```

## Crear base de datos

```sql
CREATE DATABASE ejercicio2;
```

## Configurar `application.properties`

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/ejercicio2
spring.datasource.username=root
spring.datasource.password=1234

spring.jpa.hibernate.ddl-auto=update
server.port=8080
```

## Ejecutar aplicación

```bash
mvn spring-boot:run
```

La API quedará disponible en:

`http://localhost:8080`

---

# API Endpoints

## Alumno

| Método | Endpoint |
|--------|----------|
| GET    | `/alumno` |
| POST   | `/alumno` |
| PUT    | `/alumno` |
| DELETE | `/alumno/{id}` |

### Endpoints adicionales

| Endpoint | Descripción |
|----------|-------------|
| `/alumno/{id}/curso` | curso del alumno |
| `/alumno/{id}/notas` | notas del alumno |
| `/alumno/{id}/promedio` | promedio general |
| `/alumno/{id}/promedio/asignatura/{id}` | promedio por asignatura |
| `/alumno/{id}/informe` | informe completo del alumno |

## Curso

| Método | Endpoint |
|--------|----------|
| GET    | `/curso` |
| POST   | `/curso` |
| PUT    | `/curso` |
| DELETE | `/curso/{id}` |

## Asignatura

| Método | Endpoint |
|--------|----------|
| GET    | `/asignatura` |
| POST   | `/asignatura` |
| PUT    | `/asignatura` |
| DELETE | `/asignatura/{id}` |

## Nota

| Método | Endpoint |
|--------|----------|
| GET    | `/nota` |
| POST   | `/nota` |
| PUT    | `/nota` |
| DELETE | `/nota/{id}` |

---

# Ejemplo de uso

## Crear alumno

### Request

```http
POST /alumno
```

```json
{
  "nombre": "####",
  "rut": "########",
  "curso": {
    "id": 1
  }
}
```

### Response

```json
{
  "id": 1,
  "nombre": "####",
  "rut": "########",
  "curso": {
    "id": 1
  }
}
```

## Registrar nota

### Request

```http
POST /nota
```

```json
{
  "alumno": {
    "id": 1
  },
  "asignatura": {
    "id": 1
  },
  "nota": 6.0
}
```

### Response

```json
{
  "id": 5,
  "alumno": {
    "id": 1
  },
  "asignatura": {
    "id": 1
  },
  "nota": 6.0
}
```

---

# Reglas de negocio

El sistema implementa varias validaciones:

- validación de RUT chileno
- un alumno no puede eliminarse si tiene notas
- las notas deben estar entre 2.0 y 7.0
- cálculo automático de promedio general
- cálculo de promedio por asignatura
- generación de informe académico

---

# Features

- CRUD de alumnos
- CRUD de cursos
- CRUD de asignaturas
- registro de notas
- cálculo de promedios
- informe académico por alumno
- validación de RUT chileno

---

# Roadmap

Mejoras futuras:

- Spring Security
- JWT authentication
- Swagger / OpenAPI
- Docker
- Tests unitarios
- CI/CD

---

# Autor

Juan Correa  
Backend Developer

GitHub:  
<https://github.com/juandev201305>

---

# Licencia

Actualmente el proyecto no tiene licencia definida.

Se recomienda usar:

- MIT
- Apache 2.0
