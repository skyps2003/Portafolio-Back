package IHC.Portafolio.Entity;


import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "usuario")
@Getter
@Setter
public class TUsuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "nombre")
    private String nombre;

    @Column(name = "apellidos")
    private String apellidos;

    @Column(name = "profesion")
    private String profesion;

    @Column(name = "email")
    private String email;

    @Column(name = "celular")
    private String celular;

    @Column(name = "contraseña") 
    private String contrasena;

    @Column(name = "foto_url")
    private String fotoUrl;

    @Column(name = "resumen", columnDefinition = "TEXT")
    private String resumen;

    @Column(name = "fecha_nacimiento")
    private Date fechaNacimiento;

    @Column(name = "token")
    private String token;

    @Column(name = "activo")
    private boolean activo;

}
