package IHC.Portafolio.Service.Proyecto;

import IHC.Portafolio.Business.BusinessProyecto;
import IHC.Portafolio.Dto.DtoProyecto;
import IHC.Portafolio.Service.Generic.ResponseGeneric;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/proyecto")
public class ProyectoController {

    @Autowired
    private BusinessProyecto business;
    /*
     * G U A R D A R
     */

    @PostMapping("/guardar")
    public ResponseEntity<ResponseGeneric> guardar(@RequestBody DtoProyecto dto) {
        ResponseGeneric response = new ResponseGeneric();
        try {
            String mensaje = business.guardar(dto);
            response.mo.addResponseMesssage(mensaje);
            response.mo.setSuccess();
        } catch (Exception e) {
            response.mo.addResponseMesssage("Error al guardar el proyecto.");
            response.mo.setException();
        }
        return ResponseEntity.ok(response);
    }
    /*
     * L I S T A R  P O R  U S U A R I O
     */

    @GetMapping("/usuario/{id}")
    public ResponseEntity<List<DtoProyecto>> listarPorUsuario(@PathVariable Long id) {
        return ResponseEntity.ok(business.getAllByUsuario(id));
    }
    /*
     * E L I M I N A R
     */

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<ResponseGeneric> eliminar(@PathVariable Long id) {
        ResponseGeneric response = new ResponseGeneric();
        try {
            String mensaje = business.eliminar(id);
            response.mo.addResponseMesssage(mensaje);
            if (mensaje.contains("no encontrado")) {
                response.mo.setError();
            } else {
                response.mo.setSuccess();
            }
        } catch (Exception e) {
            response.mo.addResponseMesssage("Error al eliminar el proyecto.");
            response.mo.setException();
        }
        return ResponseEntity.ok(response);
    }
    /*
     * A C T U A L I Z A R   V I S I B I L I D A D
     */

    @PutMapping("/visible/{id}")
    public ResponseEntity<ResponseGeneric> actualizarVisibilidad(
            @PathVariable Long id,
            @RequestParam boolean visible) {

        ResponseGeneric response = new ResponseGeneric();
        try {
            String mensaje = business.actualizarVisibilidad(id, visible);
            response.mo.addResponseMesssage(mensaje);
            if (mensaje.contains("no encontrado")) {
                response.mo.setError();
            } else {
                response.mo.setSuccess();
            }
        } catch (Exception e) {
            response.mo.addResponseMesssage("Error al actualizar visibilidad del proyecto.");
            response.mo.setException();
        }
        return ResponseEntity.ok(response);
    }
}
