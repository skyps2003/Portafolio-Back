package IHC.Portafolio.Business;

import IHC.Portafolio.Dto.DtoPlantillaPortafolio;
import IHC.Portafolio.Entity.TPlantillaPortafolio;
import IHC.Portafolio.Repository.RepoPlantillaPortafolio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BusinessPlantillaPortafolio {

    @Autowired
    private RepoPlantillaPortafolio repo;

    public String guardar(DtoPlantillaPortafolio dto) {
        if (repo.existsByNombre(dto.getNombre())) {
            return "La plantilla con ese nombre ya existe.";
        }
        TPlantillaPortafolio p = new TPlantillaPortafolio();
        p.setNombre(dto.getNombre());
        p.setDescripcion(dto.getDescripcion());
        p.setEstilosBase(dto.getEstilosBase());
        repo.save(p);
        return "Plantilla guardada correctamente.";
    }

    public List<DtoPlantillaPortafolio> getAll() {
        List<TPlantillaPortafolio> lista = repo.findAll();
        List<DtoPlantillaPortafolio> dtoList = new ArrayList<>();
        for (TPlantillaPortafolio p : lista) {
            DtoPlantillaPortafolio dto = new DtoPlantillaPortafolio();
            dto.setId(p.getId());
            dto.setNombre(p.getNombre());
            dto.setDescripcion(p.getDescripcion());
            dto.setEstilosBase(p.getEstilosBase());
            dtoList.add(dto);
        }
        return dtoList;
    }

    public DtoPlantillaPortafolio getById(Long id) {
        TPlantillaPortafolio p = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Plantilla no encontrada"));
        DtoPlantillaPortafolio dto = new DtoPlantillaPortafolio();
        dto.setId(p.getId());
        dto.setNombre(p.getNombre());
        dto.setDescripcion(p.getDescripcion());
        dto.setEstilosBase(p.getEstilosBase());
        return dto;
    }
}
