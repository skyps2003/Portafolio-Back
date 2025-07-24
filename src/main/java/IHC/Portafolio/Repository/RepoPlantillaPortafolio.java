package IHC.Portafolio.Repository;

import IHC.Portafolio.Entity.TPlantillaPortafolio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepoPlantillaPortafolio extends JpaRepository<TPlantillaPortafolio, Long> {
    boolean existsByNombre(String nombre);
}
