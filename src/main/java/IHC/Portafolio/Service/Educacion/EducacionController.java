package IHC.Portafolio.Service.Educacion;

import IHC.Portafolio.Business.BusinessEducacion;
import IHC.Portafolio.Dto.DtoEducacion;
import IHC.Portafolio.Service.Educacion.ResponseObject.ResponseGetAll;
import IHC.Portafolio.Service.Generic.ResponseGeneric;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/educacion")
public class EducacionController {

    @Autowired
    private BusinessEducacion business;

    // Obtener todas las educaciones de un usuario con mensajes
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<ResponseGetAll> getAllByUsuario(@PathVariable Long usuarioId) {
        ResponseGetAll response = new ResponseGetAll();
        try {
            List<DtoEducacion> lista = business.getAllByUsuario(usuarioId);
            for (DtoEducacion item : lista) {
                Map<String, Object> map = new HashMap<>();
                map.put("id", item.getId());
                map.put("institucion", item.getInstitucion());
                map.put("titulo", item.getTitulo());
                map.put("fecha_inicio", item.getFechaInicio());
                map.put("fecha_fin", item.getFechaFin());
                map.put("usuario_id", item.getUsuarioId());
                response.dto.listTEducacion.add(map);
            }
            response.mo.setSuccess();
            response.mo.addResponseMesssage("Educaciones obtenidas correctamente.");
        } catch (Exception e) {
            response.mo.setException();
            response.mo.addResponseMesssage("Error al obtener educaciones del usuario.");
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // Crear nueva educación con mensajes
    @PostMapping("/guardar")
    public ResponseEntity<ResponseGeneric> guardar(@RequestBody DtoEducacion dto) {
        ResponseGeneric response = new ResponseGeneric();
        try {
            DtoEducacion guardado = business.save(dto);
            response.mo.setSuccess();
            response.mo.addResponseMesssage("Educación guardada correctamente con ID " + guardado.getId());
        } catch (Exception e) {
            response.mo.setException();
            response.mo.addResponseMesssage("Error al guardar educación: " + e.getMessage());
        }
        return ResponseEntity.ok(response);
    }

    // Eliminar educación por id con mensajes
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<ResponseGeneric> eliminar(@PathVariable Long id) {
        ResponseGeneric response = new ResponseGeneric();
        try {
            business.delete(id);
            response.mo.setSuccess();
            response.mo.addResponseMesssage("Educación eliminada correctamente.");
        } catch (Exception e) {
            response.mo.setException();
            response.mo.addResponseMesssage("Error al eliminar educación: " + e.getMessage());
        }
        return ResponseEntity.ok(response);
    }
}
