# LÆSE Gallery — Proyecto Final 3ª Evaluación

## Estructura del proyecto

```
laese-gallery/
├── frontend/               ← HTML/CSS/JS (Bootstrap 5)
│   ├── index.html          ← Página de entrada (splash)
│   ├── main.html           ← Galería principal
│   ├── style/
│   │   ├── index.css
│   │   └── main.css
│   ├── script/
│   │   └── script.js       ← Llama a la API REST
│   └── img/                ← Imágenes de los relojes
│
└── backend/                ← API REST con Spring Boot + JPA
    ├── pom.xml
    └── src/main/
        ├── java/com/laese/gallery/
        │   ├── GalleryApplication.java     ← Punto de entrada
        │   ├── controller/
        │   │   └── RelojController.java    ← Endpoints REST (CRUD completo)
        │   ├── model/
        │   │   └── Reloj.java              ← Entidad JPA (@Entity)
        │   ├── repository/
        │   │   └── RelojRepository.java    ← Spring Data JPA
        │   └── service/
        │       └── RelojService.java       ← Lógica de negocio
        └── resources/
            └── application.properties     ← Config PostgreSQL
```

---

## Requisitos previos

- Java 21
- Maven (o usar el wrapper `./mvnw`)
- PostgreSQL instalado y en ejecución

---

## Configuración de la base de datos

1. Abre pgAdmin o psql y crea la base de datos:

```sql
CREATE DATABASE laese_db;
```

2. (Opcional) Si tu usuario/contraseña de PostgreSQL es diferente a `postgres/postgres`,
   edita `backend/src/main/resources/application.properties`:

```properties
spring.datasource.username=TU_USUARIO
spring.datasource.password=TU_PASSWORD
```

Spring Boot creará la tabla `relojes` automáticamente al arrancar
gracias a `spring.jpa.hibernate.ddl-auto=update`.

---

## Cómo ejecutar

### Frontend
Abre `frontend/index.html` con **Live Server** (VS Code).
Se sirve en `http://localhost:5500`.

### Backend (Spring Boot)

```bash
cd backend
./mvnw spring-boot:run        # Linux / macOS
mvnw.cmd spring-boot:run      # Windows
```

O desde IntelliJ / Eclipse: botón ▶ sobre `GalleryApplication`.

La API arranca en `http://localhost:8080`.
Los datos iniciales (8 relojes) se insertan automáticamente si la tabla está vacía.

---

## Endpoints de la API

| Método | URL                    | Descripción                  |
|--------|------------------------|------------------------------|
| GET    | /api/relojes           | Lista todos los relojes      |
| GET    | /api/relojes/{id}      | Obtiene un reloj por id      |
| POST   | /api/relojes           | Crea un nuevo reloj          |
| PUT    | /api/relojes/{id}      | Actualiza un reloj existente |
| DELETE | /api/relojes/{id}      | Elimina un reloj             |

### Ejemplo — crear reloj con cURL

```bash
curl -X POST http://localhost:8080/api/relojes \
  -H "Content-Type: application/json" \
  -d '{"titulo":"Rolex Submariner","texto":"Descripción...","imagen":"img/sub.png"}'
```

---

## Cambios respecto al proyecto del 2º trimestre

| Aspecto        | Antes                         | Ahora                          |
|----------------|-------------------------------|--------------------------------|
| Estilos        | CSS propio                    | Bootstrap 5 + CSS personalizado|
| Datos          | `data.xml` leído con fetch    | API REST Spring Boot + JPA     |
| Persistencia   | Sin persistencia real         | PostgreSQL vía Spring Data JPA |
| Comunicación   | `DOMParser` sobre XML         | `fetch` con JSON               |
| Modales        | CSS `.activo` manual          | `bootstrap.Modal`              |
| Responsivo     | CSS Grid manual               | Bootstrap Grid `row-cols-*`    |
| Notificaciones | `alert()`                     | Toast de Bootstrap             |
