package IHC.Portafolio.Business;

import IHC.Portafolio.Entity.TProyecto;
import IHC.Portafolio.Dto.DtoProyecto;
import IHC.Portafolio.Repository.RepoProyecto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BusinessProyecto {

    @Autowired
    private RepoProyecto repo;
    /*
    * G U A R D A R
    */
    public String guardar(DtoProyecto dto) {
        TProyecto entity = new TProyecto();
        entity.setTitulo(dto.getTitulo());
        entity.setDescripcion(dto.getDescripcion());
        entity.setImagenUrl(dto.getImagenUrl());
        entity.setFechaInicio(dto.getFechaInicio());
        entity.setFechaFin(dto.getFechaFin());
        entity.setEnlaceExterno(dto.getEnlaceExterno());
        entity.setUsuarioId(dto.getUsuarioId());
        entity.setVisiblePortafolio(dto.isVisiblePortafolio());
        repo.save(entity);
        return "Proyecto guardado correctamente.";
    }
    /*
     * L I S T A R  P O R  U S U A R I O
     */
    public List<DtoProyecto> getAllByUsuario(Long usuarioId) {
        List<TProyecto> proyectos = repo.findByUsuarioId(usuarioId);
        List<DtoProyecto> lista = new ArrayList<>();
        for (TProyecto item : proyectos) {
            DtoProyecto dto = new DtoProyecto();
            dto.setId(item.getId());
            dto.setTitulo(item.getTitulo());
            dto.setDescripcion(item.getDescripcion());
            dto.setImagenUrl(item.getImagenUrl());
            dto.setFechaInicio(item.getFechaInicio());
            dto.setFechaFin(item.getFechaFin());
            dto.setEnlaceExterno(item.getEnlaceExterno());
            dto.setUsuarioId(item.getUsuarioId());
            dto.setVisiblePortafolio(item.isVisiblePortafolio());
            lista.add(dto);
        }
        return lista;
    }
    /*
     * E L I M I N A R
     */

    public String eliminar(Long id) {
        Optional<TProyecto> opt = repo.findById(id);
        if (opt.isPresent()) {
            repo.deleteById(id);
            return "Proyecto eliminado correctamente.";
        } else {
            return "Proyecto no encontrado.";
        }
    }
    /* 
     * A C T U A L I Z A R   V I S I B I L I D A D
     */
    public String actualizarVisibilidad(Long id, boolean visible) {
        Optional<TProyecto> opt = repo.findById(id);
        if (opt.isPresent()) {
            TProyecto proyecto = opt.get();
            proyecto.setVisiblePortafolio(visible);
            repo.save(proyecto);
            return "Visibilidad del proyecto actualizada.";
        } else {
            return "Proyecto no encontrado.";
        }
    }
}

