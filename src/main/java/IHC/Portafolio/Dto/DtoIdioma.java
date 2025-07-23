package IHC.Portafolio.Dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DtoIdioma {
    private Long id;
    private String nombre;
    private String nivel;  // Si quieres manejar el nivel directamente en el DTO
}
