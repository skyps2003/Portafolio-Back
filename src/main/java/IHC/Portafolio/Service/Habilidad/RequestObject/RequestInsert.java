package IHC.Portafolio.Service.Habilidad.RequestObject;


import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class RequestInsert {
    @NotBlank(message = "El campo \"nombre\" es requerido")
    private String nombre;
    
}
