package IHC.Portafolio.Service.Plantilla;

import IHC.Portafolio.Business.BusinessPlantillaPortafolio;
import IHC.Portafolio.Dto.DtoPlantillaPortafolio;
import IHC.Portafolio.Service.Plantilla.ResponseObject.ResponseGetAll;
import IHC.Portafolio.Service.Generic.ResponseGeneric;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/plantilla")
public class PlantillaPortafolioController {

    @Autowired
    private BusinessPlantillaPortafolio business;

    /*
     * GUARDAR
     */
    @PostMapping("/guardar")
    public ResponseEntity<ResponseGeneric> guardar(@RequestBody DtoPlantillaPortafolio dto) {
        ResponseGeneric response = new ResponseGeneric();
        try {
            String mensaje = business.guardar(dto);
            response.mo.addResponseMesssage(mensaje);
            if (mensaje.contains("ya existe")) {
                response.mo.setError();
            } else {
                response.mo.setSuccess();
            }
        } catch (Exception e) {
            response.mo.addResponseMesssage("Error al guardar plantilla.");
            response.mo.setException();
        }
        return ResponseEntity.ok(response);
    }

    /*
     * LISTAR TODAS
     */
    @GetMapping("/todas")
    public ResponseEntity<ResponseGetAll> getAll() {
        ResponseGetAll response = new ResponseGetAll();
        try {
            List<DtoPlantillaPortafolio> lista = business.getAll();

            for (DtoPlantillaPortafolio item : lista) {
                Map<String, Object> map = new HashMap<>();
                map.put("id", item.getId());
                map.put("nombre", item.getNombre());
                map.put("descripcion", item.getDescripcion());
                map.put("estilosBase", item.getEstilosBase());
                response.dto.listTPlantilla.add(map);
            }

            response.mo.addResponseMesssage("Plantillas obtenidas correctamente.");
            response.mo.setSuccess();
        } catch (Exception e) {
            response.mo.addResponseMesssage("Error al obtener plantillas.");
            response.mo.setException();
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /*
     * OBTENER POR ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<ResponseGetAll> getById(@PathVariable Long id) {
        ResponseGetAll response = new ResponseGetAll();
        try {
            DtoPlantillaPortafolio item = business.getById(id);

            Map<String, Object> map = new HashMap<>();
            map.put("id", item.getId());
            map.put("nombre", item.getNombre());
            map.put("descripcion", item.getDescripcion());
            map.put("estilosBase", item.getEstilosBase());

            response.dto.listTPlantilla.add(map);
            response.mo.addResponseMesssage("Plantilla obtenida correctamente.");
            response.mo.setSuccess();
        } catch (Exception e) {
            response.mo.addResponseMesssage("Error al obtener plantilla.");
            response.mo.setException();
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
