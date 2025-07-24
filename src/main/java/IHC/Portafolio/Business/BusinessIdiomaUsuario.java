package IHC.Portafolio.Business;

import IHC.Portafolio.Dto.DtoIdioma;
import IHC.Portafolio.Dto.DtoIdiomaUsuario;
import IHC.Portafolio.Entity.TIdioma;
import IHC.Portafolio.Entity.TIdiomaUsuario;
import IHC.Portafolio.Entity.TUsuario;
import IHC.Portafolio.Repository.RepoIdioma;
import IHC.Portafolio.Repository.RepoIdiomaUsuario;
import IHC.Portafolio.Repository.RepoUsuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BusinessIdiomaUsuario {

    @Autowired
    private RepoIdiomaUsuario repoIdiomaUsuario;

    @Autowired
    private RepoUsuario repoUsuario;

    @Autowired
    private RepoIdioma repoIdioma;

    public String asignarIdioma(DtoIdiomaUsuario dto) {
        if (repoIdiomaUsuario.existsByUsuarioIdAndIdiomaId(dto.getUsuarioId(), dto.getIdiomaId())) {
            return "Ya existe este idioma para el usuario.";
        }

        TUsuario usuario = repoUsuario.findById(dto.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        TIdioma idioma = repoIdioma.findById(dto.getIdiomaId())
                .orElseThrow(() -> new RuntimeException("Idioma no encontrado"));

        TIdiomaUsuario iu = new TIdiomaUsuario();
        iu.setUsuario(usuario);
        iu.setIdioma(idioma);
        iu.setNivel(dto.getNivel());

        repoIdiomaUsuario.save(iu);
        return "Idioma asignado correctamente.";
    }

    public List<DtoIdioma> getIdiomasPorUsuario(Long usuarioId) {
        List<TIdiomaUsuario> lista = repoIdiomaUsuario.findByUsuarioId(usuarioId);
        List<DtoIdioma> idiomas = new ArrayList<>();

        for (TIdiomaUsuario item : lista) {
            TIdioma idioma = item.getIdioma();
            DtoIdioma dto = new DtoIdioma();
            dto.setId(idioma.getId());
            dto.setNombre(idioma.getNombre());
            dto.setNivel(item.getNivel());
            idiomas.add(dto);
        }
        return idiomas;
    }

    public String eliminarIdioma(DtoIdiomaUsuario dto) {
        List<TIdiomaUsuario> lista = repoIdiomaUsuario.findByUsuarioId(dto.getUsuarioId());

        for (TIdiomaUsuario iu : lista) {
            if (iu.getIdioma().getId().equals(dto.getIdiomaId())) {
                repoIdiomaUsuario.delete(iu);
                return "Idioma eliminado correctamente.";
            }
        }
        return "No se encontró el idioma asignado al usuario.";
    }
}
