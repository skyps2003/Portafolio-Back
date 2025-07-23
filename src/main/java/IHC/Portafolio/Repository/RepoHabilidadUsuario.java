package IHC.Portafolio.Repository;

import IHC.Portafolio.Entity.THabilidadUsuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RepoHabilidadUsuario extends JpaRepository<THabilidadUsuario, Long> {
    List<THabilidadUsuario> findByUsuarioId(Long usuarioId);
    boolean existsByUsuarioIdAndHabilidadId(Long usuarioId, Long habilidadId);
    List<THabilidadUsuario> findByHabilidadId(Long habilidadId);

}
