package com.laese.gallery.repository;

/*
 * ══════════════════════════════════════════════════════════════
 * OPCIÓN B — Repositorio JPA para PostgreSQL
 *
 * Este archivo sólo se usa cuando activas JPA en el pom.xml
 * y en application.properties.
 *
 * Para activarlo:
 *  1. Descomenta los imports y la interfaz de abajo.
 *  2. Descomenta las anotaciones @Entity en Reloj.java.
 *  3. Descomenta la dependencia JPA en pom.xml.
 *  4. En RelojService.java, sustituye la implementación
 *     "en memoria" por la implementación JPA (ver comentarios
 *     dentro de RelojService.java).
 * ══════════════════════════════════════════════════════════════
 */

// import org.springframework.data.jpa.repository.JpaRepository;
// import org.springframework.stereotype.Repository;
// import com.laese.gallery.model.Reloj;
//
// @Repository
// public interface RelojRepository extends JpaRepository<Reloj, Long> {
//     // Spring Data JPA genera automáticamente los métodos CRUD.
//     // Puedes añadir aquí consultas personalizadas, por ejemplo:
//     // List<Reloj> findByTituloContainingIgnoreCase(String titulo);
// }
