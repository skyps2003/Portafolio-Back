package IHC.Portafolio.Business;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import IHC.Portafolio.Dto.DtoUsuario;
import IHC.Portafolio.Entity.TRecuperacionCuenta;
import IHC.Portafolio.Entity.TUsuario;
import IHC.Portafolio.Repository.RepoRecuperacionCuenta;
import IHC.Portafolio.Repository.RepoUsuario;

import jakarta.transaction.Transactional;

@Service
public class BusinessUsuario {

    @Autowired
    private RepoUsuario repoUsuario;
    @Autowired
    private RepoRecuperacionCuenta repoRecuperacion;

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
     * REGISTRO CON TOKEN
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

        String token = UUID.randomUUID().toString();
        tUsuario.setToken(token);
        tUsuario.setActivo(false);

        repoUsuario.save(tUsuario);
        return token;
    }

    /*************************
    * CONFIRMAR EMAIL
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
     * LOGIN CON HASH
     **************************/
    public boolean loginConHash(DtoUsuario dtoUsuario, PasswordEncoder passwordEncoder) {
        Optional<TUsuario> optional = repoUsuario.findByEmail(dtoUsuario.getEmail());
        if (!optional.isPresent()) {
            return false;
        }

        TUsuario usuario = optional.get();

        if (!passwordEncoder.matches(dtoUsuario.getContraseña(), usuario.getContraseña())) {
            return false;
        }

        return usuario.isActivo();
    }

    /*************************
     * UPDATE CON HASH
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

        if (dtoUsuario.getContraseña() != null && !dtoUsuario.getContraseña().isEmpty()) {
            usuario.setContraseña(dtoUsuario.getContraseña());
        }

        usuario.setFotoUrl(dtoUsuario.getFotoUrl());
        usuario.setResumen(dtoUsuario.getResumen());
        usuario.setFechaNacimiento(dtoUsuario.getFechaNacimiento());
        usuario.setToken(dtoUsuario.getToken());
        usuario.setActivo(dtoUsuario.isActivo());

        repoUsuario.save(usuario);
        return true;
    }

    /*************************
     * UPDATE SOLO FOTO Y RESUMEN
     **************************/
    @Transactional
    public boolean updateFotoResumen(Long id, String fotoUrl, String resumen) {
        Optional<TUsuario> optional = repoUsuario.findById(id);
        if (!optional.isPresent()) {
            return false;
        }

        TUsuario usuario = optional.get();
        if (fotoUrl != null && !fotoUrl.isEmpty()) {
            usuario.setFotoUrl(fotoUrl);
        }
        if (resumen != null && !resumen.isEmpty()) {
            usuario.setResumen(resumen);
        }

        repoUsuario.save(usuario);
        return true;
    }
    /*
     * Recuperación de cuenta
     */
    @Transactional
    public String solicitarRecuperacion(String email) {
        Optional<TUsuario> usuarioOpt = repoUsuario.findByEmail(email);
        if (!usuarioOpt.isPresent()) {
            throw new RuntimeException("Usuario no encontrado.");
        }

        TUsuario usuario = usuarioOpt.get();

        TRecuperacionCuenta rec = new TRecuperacionCuenta();
        rec.setUsuario(usuario);
        rec.setToken(UUID.randomUUID().toString());
        rec.setFechaExpiracion(new Date(System.currentTimeMillis() + (30 * 60 * 1000))); // 30 min
        rec.setUsado(false);

        repoRecuperacion.save(rec);

        return rec.getToken();
    }
    /*
     * Recuperación de cuenta con token
     */
    @Transactional
    public boolean restablecerPassword(String token, String nuevaPassword, PasswordEncoder encoder) {
        Optional<TRecuperacionCuenta> recOpt = repoRecuperacion.findByToken(token);
        if (!recOpt.isPresent()) return false;

        TRecuperacionCuenta rec = recOpt.get();

        if (rec.isUsado() || rec.getFechaExpiracion().before(new Date())) {
            return false;
        }

        TUsuario usuario = rec.getUsuario();
        usuario.setContraseña(encoder.encode(nuevaPassword));
        rec.setUsado(true);

        repoUsuario.save(usuario);
        repoRecuperacion.save(rec);

        return true;
    }
    /*
     * 
     */


}
