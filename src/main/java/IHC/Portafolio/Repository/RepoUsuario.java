package IHC.Portafolio.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import IHC.Portafolio.Entity.TUsuario;

public interface RepoUsuario extends JpaRepository<TUsuario, Long> {
    Optional<TUsuario> findByEmail(String email);
    Optional<TUsuario> findByEmailAndContrasena(String email, String contrasena);
    Optional<TUsuario> findByToken(String token);
    }
