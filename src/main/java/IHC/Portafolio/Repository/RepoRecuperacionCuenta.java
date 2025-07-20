package IHC.Portafolio.Repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import IHC.Portafolio.Entity.TRecuperacionCuenta;

public interface RepoRecuperacionCuenta extends JpaRepository<TRecuperacionCuenta, Long> {
    Optional<TRecuperacionCuenta> findByToken(String token);
}
