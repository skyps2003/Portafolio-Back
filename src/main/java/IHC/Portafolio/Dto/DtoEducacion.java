package IHC.Portafolio.Dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class DtoEducacion {
    private Long id;
    private String institucion;
    private String titulo;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private Long usuarioId;
}
