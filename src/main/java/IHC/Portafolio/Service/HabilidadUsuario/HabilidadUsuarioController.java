package IHC.Portafolio.Service.HabilidadUsuario;

import IHC.Portafolio.Business.BusinessHabilidadUsuario;
import IHC.Portafolio.Dto.DtoHabilidad;
import IHC.Portafolio.Dto.DtoHabilidadUsuario;
import IHC.Portafolio.Service.Generic.ResponseGeneric;
import IHC.Portafolio.Service.HabilidadUsuario.ResponseObject.ResponseGetAll;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/habilidad-usuario")
public class HabilidadUsuarioController {

    @Autowired
    private BusinessHabilidadUsuario business;

  @PostMapping("/asignar")
    public ResponseEntity<ResponseGeneric> asignar(@RequestBody DtoHabilidadUsuario dto) {
        ResponseGeneric response = new ResponseGeneric();
        try {
            String mensaje = business.asignarHabilidad(dto);
            response.mo.addResponseMesssage(mensaje);  // Siempre añadimos el mensaje

            if (mensaje.toLowerCase().contains("error") || mensaje.toLowerCase().contains("ya existe")) {
                response.mo.setError();
            } else {
                response.mo.setSuccess();
            }
        } catch (Exception e) {
            response.mo.addResponseMesssage("Error al asignar la habilidad.");
            response.mo.setException();
        }
        return ResponseEntity.ok(response);
    }

    /*
     * Obtiene las habilidades de un usuario
     */
    @GetMapping("/usuario/{id}")
    public ResponseEntity<ResponseGetAll> getHabilidadesPorUsuario(@PathVariable Long id) {
        ResponseGetAll response = new ResponseGetAll();
        try {
            List<DtoHabilidad> lista = business.getHabilidadesPorUsuario(id);

            for (DtoHabilidad item : lista) {
                Map<String, Object> map = new HashMap<>();
                map.put("id", item.getId());
                map.put("nombre", item.getNombre());
                response.dto.listTHabilidad.add(map);
            }

            response.mo.setSuccess();
        } catch (Exception e) {
            response.mo.addResponseMesssage("Error al obtener habilidades del usuario.");
            response.mo.setException();
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    /*
     * Elimina una habilidad de un usuario
     */
    
    @DeleteMapping("/eliminar")
    public ResponseEntity<ResponseGeneric> eliminar(@RequestBody DtoHabilidadUsuario dto) {
        ResponseGeneric response = new ResponseGeneric();
        try {
            String mensaje = business.eliminarHabilidad(dto);
            response.mo.addResponseMesssage(mensaje);

            if (mensaje.toLowerCase().contains("error") || mensaje.toLowerCase().contains("no se encontró")) {
                response.mo.setError();
            } else {
                response.mo.setSuccess();
            }
        } catch (Exception e) {
            response.mo.addResponseMesssage("Error al eliminar la habilidad.");
            response.mo.setException();
        }
        return ResponseEntity.ok(response);
    }

}
