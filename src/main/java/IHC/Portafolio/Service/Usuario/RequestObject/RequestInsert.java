package IHC.Portafolio.Service.Usuario.RequestObject;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class RequestInsert {
    @NotBlank(message = "El campo \"nombre\" es requerido")
    private String nombre;
    @NotBlank(message = "El campo \"apellidos\" es requerido")
    private String apellidos;
    @NotBlank(message = "El campo \"email\" es requerido")
    private String email;
    @NotBlank(message = "El campo \"contraseña\" es requerido")
    private String contraseña;
    @NotBlank(message = "El campo \"celular\" es requerido")
    private String celular;
    @NotBlank(message = "El campo \"profesion\" es requerido")
    private String profesion;
     @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate fechaNacimiento;
}
