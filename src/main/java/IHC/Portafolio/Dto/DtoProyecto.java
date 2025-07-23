package IHC.Portafolio.Dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class DtoProyecto {
    private Long id;
    private String titulo;
    private String descripcion;
    private String imagenUrl;
    private Date fechaInicio;
    private Date fechaFin;
    private String enlaceExterno;
    private Long usuarioId;
    private boolean visiblePortafolio;
}