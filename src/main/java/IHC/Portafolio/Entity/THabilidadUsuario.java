package IHC.Portafolio.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "usuario_habilidad", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"usuario_id", "habilidad_id"})
})
@Getter
@Setter
public class THabilidadUsuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private TUsuario usuario;

    @ManyToOne
    @JoinColumn(name = "habilidad_id", nullable = false)
    private THabilidad habilidad;
}
