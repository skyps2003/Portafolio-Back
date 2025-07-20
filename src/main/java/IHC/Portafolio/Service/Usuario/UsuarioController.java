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

import IHC.Portafolio.Business.BusinessUsuario;
import IHC.Portafolio.Dto.DtoUsuario;
import IHC.Portafolio.Service.Generic.EmailService;
import IHC.Portafolio.Service.Usuario.RequestObject.RequestInsert;
import IHC.Portafolio.Service.Usuario.ResponseObject.ResponseGetAll;
import IHC.Portafolio.Service.Usuario.ResponseObject.ResponseInsert;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    private EmailService emailService;

    @Autowired
    private BusinessUsuario businessUsuario;

    /**
     * Obtener todos los usuarios (solo datos públicos).
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
     * Solo se permiten correos @unamba.edu.pe
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

        // *** VALIDACIÓN DEL DOMINIO DEL CORREO ***
        if (!request.getEmail().toLowerCase().endsWith("@unamba.edu.pe")) {
            response.mo.addResponseMesssage("Solo se permiten correos @unamba.edu.pe para registrarse.");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        DtoUsuario dto = new DtoUsuario();
        dto.setNombre(request.getNombre());
        dto.setApellidos(request.getApellidos());
        dto.setEmail(request.getEmail());
        dto.setContraseña(request.getContraseña());
        dto.setCelular(request.getCelular());
        dto.setProfesion(request.getProfesion());

        LocalDate fechaLocal = request.getFechaNacimiento();
        Date fechaDate = Date.from(fechaLocal.atStartOfDay(ZoneId.systemDefault()).toInstant());
        dto.setFechaNacimiento(fechaDate);

        // Insertar usuario y obtener token
        String token = businessUsuario.insert(dto);

        // Enviar correo de verificación
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
     * Endpoint para probar envío de email.
     */
    @GetMapping("/test-email")
    public ResponseEntity<String> testEmail(@RequestParam String email) {
        try {
            emailService.sendTestEmail(email);
            return ResponseEntity.ok("Correo de prueba enviado a " + email);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al enviar correo: " + e.getMessage());
        }
    }
}
