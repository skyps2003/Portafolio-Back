package IHC.Portafolio.Repository;

import IHC.Portafolio.Entity.TProyecto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RepoProyecto extends JpaRepository<TProyecto, Long> {
    List<TProyecto> findByUsuarioId(Long usuarioId);
}