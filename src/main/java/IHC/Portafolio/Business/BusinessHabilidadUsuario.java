package IHC.Portafolio.Business;

import IHC.Portafolio.Dto.DtoHabilidad;
import IHC.Portafolio.Dto.DtoHabilidadUsuario;
import IHC.Portafolio.Entity.THabilidad;
import IHC.Portafolio.Entity.THabilidadUsuario;
import IHC.Portafolio.Entity.TUsuario;
import IHC.Portafolio.Repository.RepoHabilidad;
import IHC.Portafolio.Repository.RepoHabilidadUsuario;
import IHC.Portafolio.Repository.RepoUsuario;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BusinessHabilidadUsuario {

    @Autowired
    private RepoHabilidadUsuario repoHabilidadUsuario;

    @Autowired
    private RepoUsuario repoUsuario;

    @Autowired
    private RepoHabilidad repoHabilidad;

    public String asignarHabilidad(DtoHabilidadUsuario dto) {
        if (repoHabilidadUsuario.existsByUsuarioIdAndHabilidadId(dto.getUsuarioId(), dto.getHabilidadId())) {
            return "Ya existe esta habilidad para el usuario.";
        }

        TUsuario usuario = repoUsuario.findById(dto.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        THabilidad habilidad = repoHabilidad.findById(dto.getHabilidadId())
                .orElseThrow(() -> new RuntimeException("Habilidad no encontrada"));

        THabilidadUsuario hu = new THabilidadUsuario();
        hu.setUsuario(usuario);
        hu.setHabilidad(habilidad);

        repoHabilidadUsuario.save(hu);
        return "Habilidad asignada correctamente.";
    }
    /*
     * Obtiene las habilidades de un usuario
     */
    public List<DtoHabilidad> getHabilidadesPorUsuario(Long usuarioId) {
        List<THabilidadUsuario> lista = repoHabilidadUsuario.findByUsuarioId(usuarioId);
        List<DtoHabilidad> habilidades = new ArrayList<>();

        for (THabilidadUsuario item : lista) {
            THabilidad h = item.getHabilidad();
            DtoHabilidad dto = new DtoHabilidad();
            dto.setId(h.getId());
            dto.setNombre(h.getNombre());
            habilidades.add(dto);
        }

        return habilidades;
    }
    /*
     * Elimina una habilidad de un usuario
     */
    public String eliminarHabilidad(DtoHabilidadUsuario dto) {
        // Buscar la relación usuario-habilidad
        List<THabilidadUsuario> lista = repoHabilidadUsuario.findByUsuarioId(dto.getUsuarioId());

        // Buscar la habilidad específica asignada a ese usuario
        for (THabilidadUsuario hu : lista) {
            if (hu.getHabilidad().getId().equals(dto.getHabilidadId())) {
                repoHabilidadUsuario.delete(hu);
                return "Habilidad eliminada correctamente.";
            }
        }
        return "No se encontró la habilidad asignada al usuario.";
    }


}
