package IHC.Portafolio.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "proyecto")
@Getter
@Setter
public class TProyecto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;
    private String descripcion;

    @Column(name = "imagen_url")
    private String imagenUrl;

    @Column(name = "fecha_inicio")
    private Date fechaInicio;

    @Column(name = "fecha_fin")
    private Date fechaFin;

    @Column(name = "enlace_externo")
    private String enlaceExterno;

    @Column(name = "usuario_id")
    private Long usuarioId;

    @Column(name = "visible_portafolio")
    private boolean visiblePortafolio;
}