package com.laese.gallery.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.laese.gallery.model.Reloj;
import java.util.List;

/**
 * Repositorio Spring Data JPA para la entidad Reloj.
 * Spring genera automáticamente la implementación CRUD.
 */
@Repository
public interface RelojRepository extends JpaRepository<Reloj, Long> {

    // Búsqueda por título (ignorando mayúsculas/minúsculas)
    List<Reloj> findByTituloContainingIgnoreCase(String titulo);
}
