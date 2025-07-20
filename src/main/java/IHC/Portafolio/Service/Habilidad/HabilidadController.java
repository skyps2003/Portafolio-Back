package IHC.Portafolio.Service.Habilidad;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import IHC.Portafolio.Business.BusinessHabilidad;
import IHC.Portafolio.Dto.DtoHabilidad;
import IHC.Portafolio.Service.Habilidad.ResponseObject.ResponseGetAll;

@RestController
@RequestMapping("/habilidad")
public class HabilidadController {

    @Autowired
    private BusinessHabilidad businessHabilidad;

    @GetMapping("/todos")
    public ResponseEntity<ResponseGetAll> getAll() {
        ResponseGetAll response = new ResponseGetAll();
        try {
            List<DtoHabilidad> listDtoHabilidad = businessHabilidad.getAll();

            for (DtoHabilidad item : listDtoHabilidad) {
                Map<String, Object> map = new HashMap<>();
                map.put("id", item.getId());
                map.put("nombre", item.getNombre());
                response.dto.listTHabilidad.add(map);
            }
            response.mo.setSuccess();
        } catch (Exception e) {
            response.mo.addResponseMesssage("Error al obtener habilidades.");
            response.mo.setException();
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
