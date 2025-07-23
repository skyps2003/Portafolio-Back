package IHC.Portafolio.Business;

import IHC.Portafolio.Dto.DtoIdioma;
import IHC.Portafolio.Entity.TIdioma;
import IHC.Portafolio.Repository.RepoIdioma;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BusinessIdioma {

    @Autowired
    private RepoIdioma repoIdioma;

    /*
     * L I S T A R   T O D O S
     */
    public List<DtoIdioma> getAll() {
        List<TIdioma> listTIdioma = repoIdioma.findAll();
        List<DtoIdioma> listDtoIdioma = new ArrayList<>();

        for (TIdioma item : listTIdioma) {
            DtoIdioma dto = new DtoIdioma();
            dto.setId(item.getId());
            dto.setNombre(item.getNombre());
            listDtoIdioma.add(dto);
        }
        return listDtoIdioma;
    }

    /*
     * G U A R D A R
     */
    public String save(DtoIdioma dto) {
        if (repoIdioma.existsByNombre(dto.getNombre())) {
            return "El idioma ya existe.";
        }

        TIdioma idioma = new TIdioma();
        idioma.setNombre(dto.getNombre());
        repoIdioma.save(idioma);
        return "Idioma guardado correctamente.";
    }
}
