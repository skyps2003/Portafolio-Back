package IHC.Portafolio.Business;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import IHC.Portafolio.Dto.DtoUsuario;
import IHC.Portafolio.Entity.TUsuario;
import IHC.Portafolio.Repository.RepoUsuario;

import jakarta.transaction.Transactional;

@Service
public class BusinessUsuario {

    @Autowired
    private RepoUsuario repoUsuario;

    public List<DtoUsuario> getAll() {
        List<TUsuario> listTUsuario = repoUsuario.findAll();
        List<DtoUsuario> listDtoUsuarios = new ArrayList<>();

        for (TUsuario item : listTUsuario) {
            DtoUsuario dtoUsuario = new DtoUsuario();
            dtoUsuario.setNombre(item.getNombre());
            dtoUsuario.setApellidos(item.getApellidos());
            dtoUsuario.setProfesion(item.getProfesion());
            dtoUsuario.setEmail(item.getEmail());
            dtoUsuario.setCelular(item.getCelular());
            dtoUsuario.setContraseña(item.getContraseña());
            dtoUsuario.setFotoUrl(item.getFotoUrl());
            dtoUsuario.setResumen(item.getResumen());
            dtoUsuario.setFechaNacimiento(item.getFechaNacimiento());
            dtoUsuario.setActivo(item.isActivo());
            listDtoUsuarios.add(dtoUsuario);
        }
        return listDtoUsuarios;
    }
    /************************
     * C O R R E O 
     ************************/
    @Transactional
    public String insert(DtoUsuario dtoUsuario) {
        
        TUsuario tUsuario = new TUsuario();
        tUsuario.setNombre(dtoUsuario.getNombre());
        tUsuario.setApellidos(dtoUsuario.getApellidos());
        tUsuario.setProfesion(dtoUsuario.getProfesion());
        tUsuario.setEmail(dtoUsuario.getEmail());
        tUsuario.setCelular(dtoUsuario.getCelular());
        tUsuario.setContraseña(dtoUsuario.getContraseña());
        tUsuario.setFotoUrl(dtoUsuario.getFotoUrl());
        tUsuario.setResumen(dtoUsuario.getResumen());
        tUsuario.setFechaNacimiento(dtoUsuario.getFechaNacimiento());
        
        /*****************
         * T O K E N 
         *****************/
        String token = UUID.randomUUID().toString();
        tUsuario.setToken(token);
        tUsuario.setActivo(false);

        repoUsuario.save(tUsuario);

        return token; 
    }
    /*************************
    * C O N F I R M A C I O N 
    **************************/

    @Transactional
    public boolean confirmEmail(String token) {
        Optional<TUsuario> optional = repoUsuario.findByToken(token);
        if (!optional.isPresent()) {
            return false;
        }

        TUsuario usuario = optional.get();
        usuario.setActivo(true);
        usuario.setToken(null); 
        repoUsuario.save(usuario);

        return true;
    }

    /*************************
     * L O G I N 
     **************************/

   public boolean login(DtoUsuario dtoUsuario) {
        
        Optional<TUsuario> optional = repoUsuario.findByEmail(dtoUsuario.getEmail());
        if (!optional.isPresent()) {
            return false; 
        }

        TUsuario usuario = optional.get();
        
        
        if (!usuario.getContraseña().equals(dtoUsuario.getContraseña())) {
            return false;
        }

     
        if (!usuario.isActivo()) {
            return false;
        }

        return true; 
    }
    /*************************
     * U P D A T E 
     **************************/
    @Transactional
    public boolean update(Long id, DtoUsuario dtoUsuario) {
        Optional<TUsuario> optional = repoUsuario.findById(id);
        if (!optional.isPresent()) {
            return false;
        }

        TUsuario usuario = optional.get();
        usuario.setNombre(dtoUsuario.getNombre());
        usuario.setApellidos(dtoUsuario.getApellidos());
        usuario.setProfesion(dtoUsuario.getProfesion());
        usuario.setEmail(dtoUsuario.getEmail());
        usuario.setCelular(dtoUsuario.getCelular());
        usuario.setContraseña(dtoUsuario.getContraseña());
        usuario.setFotoUrl(dtoUsuario.getFotoUrl());
        usuario.setResumen(dtoUsuario.getResumen());
        usuario.setFechaNacimiento(dtoUsuario.getFechaNacimiento());
        usuario.setToken(dtoUsuario.getToken());
        usuario.setActivo(dtoUsuario.isActivo());

        repoUsuario.save(usuario);
        return true;
    }


}
