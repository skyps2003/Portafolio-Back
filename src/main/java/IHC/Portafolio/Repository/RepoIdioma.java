package IHC.Portafolio.Repository;

import IHC.Portafolio.Entity.TIdioma;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepoIdioma extends JpaRepository<TIdioma, Long> {
    boolean existsByNombre(String nombre);
}
