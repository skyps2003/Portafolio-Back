package IHC.Portafolio.Repository;

import IHC.Portafolio.Entity.TEducacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RepoEducacion extends JpaRepository<TEducacion, Long> {
    List<TEducacion> findByUsuarioId(Long usuarioId);
}
