package IHC.Portafolio.Business;

import IHC.Portafolio.Dto.DtoEducacion;
import IHC.Portafolio.Entity.TEducacion;
import IHC.Portafolio.Repository.RepoEducacion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BusinessEducacion {

    @Autowired
    private RepoEducacion repoEducacion;

    public List<DtoEducacion> getAllByUsuario(Long usuarioId) {
        List<TEducacion> list = repoEducacion.findByUsuarioId(usuarioId);
        List<DtoEducacion> dtoList = new ArrayList<>();

        for (TEducacion e : list) {
            DtoEducacion dto = new DtoEducacion();
            dto.setId(e.getId());
            dto.setInstitucion(e.getInstitucion());
            dto.setTitulo(e.getTitulo());
            dto.setFechaInicio(e.getFechaInicio());
            dto.setFechaFin(e.getFechaFin());
            dto.setUsuarioId(e.getUsuarioId());
            dtoList.add(dto);
        }
        return dtoList;
    }

    public DtoEducacion save(DtoEducacion dto) {
        TEducacion entity = new TEducacion();
        entity.setInstitucion(dto.getInstitucion());
        entity.setTitulo(dto.getTitulo());
        entity.setFechaInicio(dto.getFechaInicio());
        entity.setFechaFin(dto.getFechaFin());
        entity.setUsuarioId(dto.getUsuarioId());

        TEducacion saved = repoEducacion.save(entity);
        dto.setId(saved.getId());
        return dto;
    }

    public void delete(Long id) {
        repoEducacion.deleteById(id);
    }
}
