package IHC.Portafolio.Repository;

import IHC.Portafolio.Entity.THabilidad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RepoHabilidad extends JpaRepository<THabilidad, Long> {
    Optional<THabilidad> findByNombre(String nombre);
    boolean existsByNombre(String nombre);
}
