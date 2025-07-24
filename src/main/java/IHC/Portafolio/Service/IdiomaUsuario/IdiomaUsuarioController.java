package IHC.Portafolio.Service.IdiomaUsuario;

import IHC.Portafolio.Business.BusinessIdiomaUsuario;
import IHC.Portafolio.Dto.DtoIdioma;
import IHC.Portafolio.Dto.DtoIdiomaUsuario;
import IHC.Portafolio.Service.Generic.ResponseGeneric;
import IHC.Portafolio.Service.IdiomaUsuario.ResponseObject.ResponseGetAll;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/idioma-usuario")
public class IdiomaUsuarioController {

    @Autowired
    private BusinessIdiomaUsuario business;

    @PostMapping("/asignar")
    public ResponseEntity<ResponseGeneric> asignar(@RequestBody DtoIdiomaUsuario dto) {
        ResponseGeneric response = new ResponseGeneric();
        try {
            String mensaje = business.asignarIdioma(dto);
            response.mo.addResponseMesssage(mensaje);
            if (mensaje.contains("Ya existe")) {
                response.mo.setError();
            } else {
                response.mo.setSuccess();
            }
        } catch (Exception e) {
            response.mo.addResponseMesssage("Error al asignar idioma.");
            response.mo.setException();
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping("/usuario/{id}")
    public ResponseEntity<ResponseGetAll> getIdiomasPorUsuario(@PathVariable Long id) {
        ResponseGetAll response = new ResponseGetAll();
        try {
            List<DtoIdioma> lista = business.getIdiomasPorUsuario(id);
            for (DtoIdioma item : lista) {
                Map<String, Object> map = new HashMap<>();
                map.put("id", item.getId());
                map.put("nombre", item.getNombre());
                map.put("nivel", item.getNivel());
                response.dto.listTIdioma.add(map);
            }
            response.mo.setSuccess();
        } catch (Exception e) {
            response.mo.addResponseMesssage("Error al obtener idiomas del usuario.");
            response.mo.setException();
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/eliminar")
    public ResponseEntity<ResponseGeneric> eliminar(@RequestBody DtoIdiomaUsuario dto) {
        ResponseGeneric response = new ResponseGeneric();
        try {
            String mensaje = business.eliminarIdioma(dto);
            response.mo.addResponseMesssage(mensaje);
            if (mensaje.contains("No se encontró")) {
                response.mo.setError();
            } else {
                response.mo.setSuccess();
            }
        } catch (Exception e) {
            response.mo.addResponseMesssage("Error al eliminar idioma.");
            response.mo.setException();
        }
        return ResponseEntity.ok(response);
    }
}
