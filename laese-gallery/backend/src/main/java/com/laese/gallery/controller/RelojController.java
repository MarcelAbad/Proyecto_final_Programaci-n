package com.laese.gallery.controller;

import com.laese.gallery.model.Reloj;
import com.laese.gallery.service.RelojService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para el recurso "Reloj".
 *
 * Endpoints disponibles:
 *
 *   GET    /api/relojes          → lista todos los relojes
 *   GET    /api/relojes/{id}     → obtiene un reloj por id
 *   POST   /api/relojes          → crea un nuevo reloj
 *   PUT    /api/relojes/{id}     → actualiza un reloj existente
 *   DELETE /api/relojes/{id}     → elimina un reloj
 */
@RestController
@RequestMapping("/api/relojes")
@CrossOrigin(origins = {
    "http://localhost:5500",
    "http://127.0.0.1:5500",
    "http://localhost:3000"
})
public class RelojController {

    private final RelojService service;

    // Inyección de dependencia por constructor (recomendada)
    public RelojController(RelojService service) {
        this.service = service;
    }

    // ── GET /api/relojes ──────────────────────────────────────
    @GetMapping
    public ResponseEntity<List<Reloj>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    // ── GET /api/relojes/{id} ─────────────────────────────────
    @GetMapping("/{id}")
    public ResponseEntity<Reloj> getById(@PathVariable Long id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // ── POST /api/relojes ─────────────────────────────────────
    @PostMapping
    public ResponseEntity<Reloj> create(@RequestBody Reloj reloj) {
        Reloj creado = service.save(reloj);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    // ── PUT /api/relojes/{id} ─────────────────────────────────
    @PutMapping("/{id}")
    public ResponseEntity<Reloj> update(
            @PathVariable Long id,
            @RequestBody Reloj datos) {

        return service.update(id, datos)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // ── DELETE /api/relojes/{id} ──────────────────────────────
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        return service.deleteById(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}
