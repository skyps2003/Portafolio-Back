package IHC.Portafolio.Dto;

import java.util.Date;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DtoUsuario {
    private String id;
    private String nombre;
    private String apellidos;
    private String profesion;
    private String email;
    private String celular;
    private String contrasena;
    private String fotoUrl;
    private String resumen;
    private Date fechaNacimiento;
    private String token;
    private boolean activo;
}
