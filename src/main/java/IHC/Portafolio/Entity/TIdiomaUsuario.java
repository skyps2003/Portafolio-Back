package IHC.Portafolio.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "usuario_idioma", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"usuario_id", "idioma_id"})
})
@Getter
@Setter
public class TIdiomaUsuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private TUsuario usuario;

    @ManyToOne
    @JoinColumn(name = "idioma_id", nullable = false)
    private TIdioma idioma;

    @Column(name = "nivel")
    private String nivel;  // Ej: Básico, Intermedio, Avanzado
}
