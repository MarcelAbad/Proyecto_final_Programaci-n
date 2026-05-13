package com.laese.gallery.model;

/**
 * Modelo de datos para un Reloj.
 *
 * ── OPCIÓN A (en memoria): usa esta clase tal cual.
 *
 * ── OPCIÓN B (JPA + PostgreSQL): descomenta las anotaciones
 *    @Entity, @Table, @Id, @GeneratedValue y el import
 *    correspondiente, y añade la dependencia JPA al pom.xml.
 */

// OPCIÓN B — descomenta estas líneas para JPA:
// import jakarta.persistence.*;
// @Entity
// @Table(name = "relojes")

public class Reloj {

    // OPCIÓN B — descomenta para JPA:
    // @Id
    // @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;
    private String texto;
    private String imagen;

    // ── Constructores ──────────────────────────────────────────

    public Reloj() {}

    public Reloj(Long id, String titulo, String texto, String imagen) {
        this.id     = id;
        this.titulo = titulo;
        this.texto  = texto;
        this.imagen = imagen;
    }

    // ── Getters y Setters ──────────────────────────────────────

    public Long getId()               { return id; }
    public void setId(Long id)        { this.id = id; }

    public String getTitulo()             { return titulo; }
    public void   setTitulo(String t)     { this.titulo = t; }

    public String getTexto()              { return texto; }
    public void   setTexto(String t)      { this.texto = t; }

    public String getImagen()             { return imagen; }
    public void   setImagen(String i)     { this.imagen = i; }
}
