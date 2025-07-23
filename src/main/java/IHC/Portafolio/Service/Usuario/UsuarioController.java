package IHC.Portafolio.Service.Usuario;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import IHC.Portafolio.Business.BusinessUsuario;
import IHC.Portafolio.Dto.DtoUsuario;
import IHC.Portafolio.Service.Generic.EmailService;
import IHC.Portafolio.Service.Usuario.RequestObject.RequestInsert;
import IHC.Portafolio.Service.Usuario.ResponseObject.ResponseGetAll;
import IHC.Portafolio.Service.Usuario.ResponseObject.ResponseInsert;
import IHC.Portafolio.security.JwtUtil;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    private EmailService emailService;

    @Autowired
    private BusinessUsuario businessUsuario;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;


    /**
     * Obtener todos los usuarios (solo datos públicos).
     * Retorna una lista de usuarios con nombre, apellidos, email y estado activo.
     */
    @GetMapping("/todos")
    public ResponseEntity<ResponseGetAll> getAll() {
        ResponseGetAll responseGetAll = new ResponseGetAll();
        try {
            List<DtoUsuario> listDtoUsuarios = businessUsuario.getAll();

            for (DtoUsuario item : listDtoUsuarios) {
                Map<String, Object> map = new HashMap<>();
                map.put("nombre", item.getNombre());
                map.put("apellidos", item.getApellidos());
                map.put("email", item.getEmail());
                map.put("activo", item.isActivo());
                responseGetAll.dto.listUsuario.add(map);
            }
            responseGetAll.mo.setSuccess();
        } catch (Exception e) {
            responseGetAll.mo.addResponseMesssage("Error al obtener usuarios.");
            responseGetAll.mo.setException();
        }
        return new ResponseEntity<>(responseGetAll, HttpStatus.OK);
    }

    /**
     * Registro de usuario con verificación de email.
     * Solo permite correos @unamba.edu.pe.
     */
    @PostMapping(path = "/registro", consumes = "multipart/form-data")
    public ResponseEntity<ResponseInsert> registrarUsuario(
            @Valid @ModelAttribute RequestInsert request,
            BindingResult bindingResult) {

        ResponseInsert response = new ResponseInsert();
        try {
            if (bindingResult.hasErrors()) {
                bindingResult.getAllErrors().forEach(error -> {
                    response.mo.addResponseMesssage(error.getDefaultMessage());
                });
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }

            if (!request.getEmail().toLowerCase().endsWith("@unamba.edu.pe")) {
                response.mo.addResponseMesssage("Solo se permiten correos @unamba.edu.pe para registrarse.");
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }

            DtoUsuario dto = new DtoUsuario();
            dto.setNombre(request.getNombre());
            dto.setApellidos(request.getApellidos());
            dto.setEmail(request.getEmail());

           
            dto.setContraseña(passwordEncoder.encode(request.getContraseña()));

            dto.setCelular(request.getCelular());
            dto.setProfesion(request.getProfesion());

            LocalDate fechaLocal = request.getFechaNacimiento();
            Date fechaDate = Date.from(fechaLocal.atStartOfDay(ZoneId.systemDefault()).toInstant());
            dto.setFechaNacimiento(fechaDate);

            String token = businessUsuario.insert(dto);
            emailService.sendVerificationEmail(dto.getEmail(), token);

            response.mo.addResponseMesssage("Registro exitoso. Verifica tu correo para confirmar la cuenta.");
            response.mo.setSuccess();
        } catch (Exception e) {
            e.printStackTrace();
            response.mo.addResponseMesssage("Error inesperado. Intenta nuevamente más tarde.");
            response.mo.setException();
        }
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /**
     * Confirmación de email mediante token.
     * Si el token es válido, activa la cuenta del usuario.
     */
    @GetMapping("/confirmar")
    public ResponseEntity<ResponseInsert> confirmarEmail(@RequestParam String token) {
        ResponseInsert response = new ResponseInsert();
        boolean ok = businessUsuario.confirmEmail(token);

        if (ok) {
            response.mo.addResponseMesssage("Email confirmado correctamente.");
            response.mo.setSuccess();
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response.mo.addResponseMesssage("Token inválido o expirado.");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Login con email y contraseña encriptada.
     * Si la cuenta no está activada, no permite el acceso.
     */
    @PostMapping("/login")
public ResponseEntity<Map<String, Object>> login(@Valid @RequestBody DtoUsuario dtoUsuario) {
    Map<String, Object> response = new HashMap<>();
    try {
        boolean isAuthenticated = businessUsuario.loginConHash(dtoUsuario, passwordEncoder);

        if (isAuthenticated) {
            // Generar token JWT
            String token = jwtUtil.generateToken(dtoUsuario);

            response.put("message", "Inicio de sesión exitoso");
            response.put("token", token);
            response.put("email", dtoUsuario.getEmail());
            return ResponseEntity.ok(response);
        } else {
            response.put("message", "Email o contraseña incorrectos, o cuenta no activada");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
    } catch (Exception e) {
        response.put("error", "Error interno: " + e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}
    /**
     * Actualización de perfil con encriptación de contraseña.
     * Si la contraseña no se envía, no se actualiza.
     */
    @PutMapping("/actualizar/{id}")
    public ResponseEntity<ResponseInsert> actualizarUsuario(
            @PathVariable Long id,
            @Valid @RequestBody DtoUsuario dtoUsuario) {

        ResponseInsert response = new ResponseInsert();
        try {
            // Encriptar contraseña si fue enviada
            if (dtoUsuario.getContraseña() != null && !dtoUsuario.getContraseña().isEmpty()) {
                dtoUsuario.setContraseña(passwordEncoder.encode(dtoUsuario.getContraseña()));
            }

            boolean actualizado = businessUsuario.update(id, dtoUsuario);

            if (actualizado) {
                response.mo.addResponseMesssage("Usuario actualizado correctamente.");
                response.mo.setSuccess();
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                response.mo.addResponseMesssage("Usuario no encontrado.");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }

        } catch (Exception e) {
            response.mo.addResponseMesssage("Error al actualizar el usuario: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    /*
     * Actualización de foto y resumen del usuario.
     * Permite actualizar solo la foto, solo el resumen o ambos.
     */

    @PutMapping("/actualizar-foto-resumen/{id}")
    public ResponseEntity<ResponseInsert> actualizarFotoResumen(
            @PathVariable Long id,
            @RequestParam(required = false) String fotoUrl,
            @RequestParam(required = false) String resumen) {

        ResponseInsert response = new ResponseInsert();
        try {
            boolean actualizado = businessUsuario.updateFotoResumen(id, fotoUrl, resumen);

            if (actualizado) {
                response.mo.addResponseMesssage("Foto y/o resumen actualizados correctamente.");
                response.mo.setSuccess();
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                response.mo.addResponseMesssage("Usuario no encontrado.");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }

        } catch (Exception e) {
            response.mo.addResponseMesssage("Error al actualizar: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /*
     * Solicitar recuperación de contraseña.
     * Envía un correo con un token para restablecer la contraseña.
     */

    @PostMapping("/recuperar")
    public ResponseEntity<ResponseInsert> solicitarRecuperacion(@RequestParam String email) {
        ResponseInsert response = new ResponseInsert();
        try {
            String token = businessUsuario.solicitarRecuperacion(email);
            emailService.sendPasswordRecoveryEmail(email, token); // NUEVO MÉTODO
            response.mo.addResponseMesssage("Correo de recuperación enviado.");
            response.mo.setSuccess();
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.mo.addResponseMesssage("Error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
    /*
     * Restablecer la contraseña usando un token.
     * El token debe ser válido y no expirado.
     */

    @PostMapping("/restablecer")
    public ResponseEntity<ResponseInsert> restablecerPassword(
            @RequestParam String token,
            @RequestParam String nuevaPassword) {

        ResponseInsert response = new ResponseInsert();
        try {
            boolean ok = businessUsuario.restablecerPassword(token, nuevaPassword, passwordEncoder);
            if (ok) {
                response.mo.addResponseMesssage("Contraseña restablecida correctamente.");
                response.mo.setSuccess();
                return ResponseEntity.ok(response);
            } else {
                response.mo.addResponseMesssage("Token inválido o expirado.");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
        } catch (Exception e) {
            response.mo.addResponseMesssage("Error interno: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    /*
     * Obtiene las habilidades de un usuario.
     * Retorna una lista de habilidades con nombre y nivel.
     */
    

}
