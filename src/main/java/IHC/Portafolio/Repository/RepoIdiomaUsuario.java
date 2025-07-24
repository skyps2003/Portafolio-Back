package IHC.Portafolio.Repository;

import IHC.Portafolio.Entity.TIdiomaUsuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RepoIdiomaUsuario extends JpaRepository<TIdiomaUsuario, Long> {
    List<TIdiomaUsuario> findByUsuarioId(Long usuarioId);
    boolean existsByUsuarioIdAndIdiomaId(Long usuarioId, Long idiomaId);
}
