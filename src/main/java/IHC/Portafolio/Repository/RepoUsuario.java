package IHC.Portafolio.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import IHC.Portafolio.Entity.TUsuario;

public interface RepoUsuario extends JpaRepository<TUsuario, Long> {
    Optional<TUsuario> findByEmail(String email);
    Optional<TUsuario> findByEmailAndContraseña(String email, String contraseña);
    Optional<TUsuario> findByToken(String token);

}
