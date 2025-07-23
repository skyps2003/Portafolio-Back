package IHC.Portafolio.Service.Educacion.RequestObject;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestUpdate {
    @NotBlank(message = "El campo \"nombre\" es requerido")
    private String nombre;

}
