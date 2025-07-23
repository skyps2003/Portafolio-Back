package IHC.Portafolio.Service.Idioma;

import IHC.Portafolio.Business.BusinessIdioma;
import IHC.Portafolio.Dto.DtoIdioma;
import IHC.Portafolio.Service.Generic.ResponseGeneric;
import IHC.Portafolio.Service.Idioma.ResponseObject.ResponseGetAll;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/idioma")
public class IdiomaController {

    @Autowired
    private BusinessIdioma businessIdioma;

    /*
     * L I S T A R
     */
    @GetMapping("/todos")
    public ResponseEntity<ResponseGetAll> getAll() {
        ResponseGetAll response = new ResponseGetAll();
        try {
            List<DtoIdioma> listDtoIdioma = businessIdioma.getAll();

            for (DtoIdioma item : listDtoIdioma) {
                Map<String, Object> map = new HashMap<>();
                map.put("id", item.getId());
                map.put("nombre", item.getNombre());
                response.dto.listTIdioma.add(map);
            }
            response.mo.setSuccess();
        } catch (Exception e) {
            response.mo.addResponseMesssage("Error al obtener idiomas.");
            response.mo.setException();
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /*
     * G U A R D A R
     */
    @PostMapping("/guardar")
    public ResponseEntity<ResponseGeneric> guardar(@RequestBody DtoIdioma dto) {
        ResponseGeneric response = new ResponseGeneric();
        try {
            String mensaje = businessIdioma.save(dto);
            response.mo.addResponseMesssage(mensaje);

            if (mensaje.contains("ya existe")) {
                response.mo.setError();
            } else {
                response.mo.setSuccess();
            }
        } catch (Exception e) {
            response.mo.addResponseMesssage("Error al guardar el idioma.");
            response.mo.setException();
        }
        return ResponseEntity.ok(response);
    }
}
