package com.laese.gallery.service;

import com.laese.gallery.model.Reloj;
import com.laese.gallery.repository.RelojRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Servicio de negocio para la gestión de relojes.
 * Implementación con Spring Data JPA + PostgreSQL.
 *
 * Los datos iniciales se insertan al arrancar la aplicación
 * solo si la tabla está vacía (ver método initData).
 */
@Service
public class RelojService implements CommandLineRunner {

    private final RelojRepository repo;

    public RelojService(RelojRepository repo) {
        this.repo = repo;
    }

    // ── CRUD ───────────────────────────────────────────────────

    public List<Reloj> findAll() {
        return repo.findAll();
    }

    public Optional<Reloj> findById(Long id) {
        return repo.findById(id);
    }

    public Reloj save(Reloj reloj) {
        return repo.save(reloj);
    }

    public Optional<Reloj> update(Long id, Reloj datos) {
        return repo.findById(id).map(r -> {
            r.setTitulo(datos.getTitulo());
            r.setTexto(datos.getTexto());
            r.setImagen(datos.getImagen());
            return repo.save(r);
        });
    }

    public boolean deleteById(Long id) {
        if (!repo.existsById(id)) return false;
        repo.deleteById(id);
        return true;
    }

    // ── Datos iniciales (se insertan solo si la BD está vacía) ─

    @Override
    public void run(String... args) {
        if (repo.count() > 0) return;

        repo.save(new Reloj(null,
            "Rolex Daydate Rose Gold 40mm Ombre Black 228235",
            "El Rolex Day-Date 40 ref. 228235 en oro rosa con esfera ombré negra es la combinación perfecta de lujo y elegancia. Su exclusivo acabado en oro Everose de 18k y su distintiva esfera degradada lo convierten en una pieza sofisticada y muy deseada.",
            "img/rolex-day-date.png"));

        repo.save(new Reloj(null,
            "Patek Philippe Nautilus",
            "El Patek Philippe Nautilus 5711 es uno de los relojes más icónicos y codiciados del mundo. Su diseño elegante con carácter deportivo, junto a su inconfundible esfera y acabados de alta relojería, lo convierten en una pieza única.",
            "img/nautilus.png"));

        repo.save(new Reloj(null,
            "Omega Speedmaster Moonwatch",
            "El Omega Speedmaster Moonwatch Moonphase Co-Axial Master Chronometer en platino es una obra maestra que combina innovación y tradición. Su esfera de oro y la complicación de fase lunar lo elevan a otro nivel de exclusividad.",
            "img/omega-speedmaster.png"));

        repo.save(new Reloj(null,
            "Rolex GMT Master II",
            "El Rolex GMT-Master II es un reloj de lujo diseñado para viajeros que necesitan seguir múltiples zonas horarias. Con su icónica función GMT, su robusta construcción y su diseño atemporal se ha convertido en un símbolo de prestigio.",
            "img/Rolex-GMT.png"));

        repo.save(new Reloj(null,
            "Hublot Big Bang Unico Dark Green Ceramic",
            "El Hublot Big Bang Unico Dark Green Ceramic destaca por su diseño audaz y su innovación técnica. Con caja de cerámica, movimiento Unico de manufactura propia y estética vanguardista, es un símbolo de exclusividad.",
            "img/hublot.png"));

        repo.save(new Reloj(null,
            "Jacob and Co. Astronomia Casino Roulette Tourbillon",
            "El Jacob and Co. Astronomia Casino Roulette Tourbillon es pura extravagancia llevada al extremo. Su diseño tridimensional con ruleta funcional y tourbillon lo convierte en una pieza única para quienes buscan espectáculo en la muñeca.",
            "img/jacobco.png"));

        repo.save(new Reloj(null,
            "Richard Mille RM 30-01",
            "El Richard Mille RM 30-01 fue diseñado para resistir las condiciones extremas del tenis profesional. Con caja de titanio y carbono, movimiento tourbillon y diseño vanguardista, combina resistencia, precisión y estilo únicos.",
            "img/richard-mille.png"));

        repo.save(new Reloj(null,
            "Tissot PRX",
            "El Tissot PRX combina estilo y funcionalidad en un diseño atemporal. Con caja de acero inoxidable y movimiento automático, es una elección perfecta para quienes buscan calidad y durabilidad.",
            "img/reloj1.jpg"));
    }
}
