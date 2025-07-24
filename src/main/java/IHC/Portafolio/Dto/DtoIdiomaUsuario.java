package IHC.Portafolio.Dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DtoIdiomaUsuario {
    private Long id;
    private Long usuarioId;
    private Long idiomaId;
    private String nivel; // Ej: Básico, Intermedio, Avanzado
}
