package IHC.Portafolio.Business;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import IHC.Portafolio.Dto.DtoHabilidad;
import IHC.Portafolio.Entity.THabilidad;
import IHC.Portafolio.Repository.RepoHabilidad;
@Service
public class BusinessHabilidad {
    @Autowired
    private RepoHabilidad repoHabilidad;
    public List<DtoHabilidad> getAll() {
        List<THabilidad> listTHabilidad = repoHabilidad.findAll();
        List<DtoHabilidad> listDtoHabilidad = new ArrayList<>();

        for (THabilidad item : listTHabilidad) {
            DtoHabilidad dtoHabilidad = new DtoHabilidad();
            dtoHabilidad.setId(item.getId());
            dtoHabilidad.setNombre(item.getNombre());
            listDtoHabilidad.add(dtoHabilidad);
        }
        return listDtoHabilidad;
    }

}
