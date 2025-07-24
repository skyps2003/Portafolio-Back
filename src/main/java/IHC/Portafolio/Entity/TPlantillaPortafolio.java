package IHC.Portafolio.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "plantilla_portafolio", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"nombre"})
})
@Getter
@Setter
public class TPlantillaPortafolio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "descripcion")
    private String descripcion;

    @Lob
    @Column(name = "estilos_base", columnDefinition = "LONGTEXT")
    private String estilosBase; // JSON
}
