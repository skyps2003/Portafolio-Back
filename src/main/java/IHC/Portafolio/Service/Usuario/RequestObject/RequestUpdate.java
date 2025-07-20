package IHC.Portafolio.Service.Usuario.RequestObject;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestUpdate {
    @NotBlank(message = "El campo \"nombre\" es requerido")
    private String nombre;
    @NotBlank(message = "El campo \"apellidos\" es requerido")
    private String apellidos;
    @NotBlank(message = "El campo \"email\" es requerido")
    private String email;
    @NotBlank(message = "El campo \"contraseña\" es requerido")
    private String contraseña;

}
