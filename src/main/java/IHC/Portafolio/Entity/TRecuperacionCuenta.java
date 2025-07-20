package IHC.Portafolio.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "recuperacion_cuenta")
@Getter
@Setter
public class TRecuperacionCuenta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "token")
    private String token;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha_expiracion")
    private Date fechaExpiracion;
    @Column(name = "usado")
    private boolean usado = false;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private TUsuario usuario;

    // Getters y Setters
}
