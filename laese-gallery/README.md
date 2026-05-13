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
└── backend/                ← API REST con Spring Boot
    ├── pom.xml
    └── src/main/
        ├── java/com/laese/gallery/
        │   ├── GalleryApplication.java     ← Punto de entrada
        │   ├── controller/
        │   │   └── RelojController.java    ← Endpoints REST
        │   ├── model/
        │   │   └── Reloj.java              ← Entidad / POJO
        │   ├── repository/
        │   │   └── RelojRepository.java    ← JPA (Opción B)
        │   └── service/
        │       └── RelojService.java       ← Lógica de negocio
        └── resources/
            └── application.properties
```

---

## Cómo ejecutar

### Frontend
Abre `frontend/index.html` con **Live Server** (VS Code) o cualquier
servidor estático. Por defecto se sirve en `http://localhost:5500`.

### Backend (Spring Boot)

```bash
cd backend
./mvnw spring-boot:run        # Linux / macOS
mvnw.cmd spring-boot:run      # Windows
```

O desde tu IDE (IntelliJ / Eclipse): botón ▶ sobre `GalleryApplication`.

La API arranca en `http://localhost:8080`.

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

## Opción A vs Opción B

### Opción A — Datos en memoria (por defecto, sin BD)
No necesitas instalar nada más. Los datos iniciales están en
`RelojService.java`. Los cambios se pierden al reiniciar el servidor.

### Opción B — PostgreSQL con Spring Data JPA
Cuando el profesor explique JPA:

1. Crea la base de datos en PostgreSQL:
   ```sql
   CREATE DATABASE laese_db;
   ```
2. Descomenta las dependencias JPA y PostgreSQL en `pom.xml`.
3. Rellena las credenciales en `application.properties`.
4. Descomenta las anotaciones `@Entity`, `@Id`, etc. en `Reloj.java`.
5. Descomenta la interfaz `RelojRepository.java`.
6. Sustituye el bloque de código en `RelojService.java`
   (hay un comentario que indica exactamente qué copiar).

---

## Cambios respecto al proyecto del 2º trimestre

| Aspecto        | Antes                         | Ahora                          |
|----------------|-------------------------------|--------------------------------|
| Estilos        | CSS propio                    | Bootstrap 5 + CSS personalizado|
| Datos          | `data.xml` leído con fetch    | API REST Spring Boot           |
| Comunicación   | `DOMParser` sobre XML         | `fetch` con JSON               |
| Modales        | CSS `.activo` manual          | `bootstrap.Modal`              |
| Responsivo     | CSS Grid manual               | Bootstrap Grid `row-cols-*`    |
| Notificaciones | `alert()`                     | Toast de Bootstrap             |
