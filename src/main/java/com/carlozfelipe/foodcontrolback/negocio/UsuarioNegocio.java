package com.carlozfelipe.foodcontrolback.negocio;

import com.carlozfelipe.foodcontrolback.dao.DireccionDao;
import com.carlozfelipe.foodcontrolback.dao.FidelizacionDao;
import com.carlozfelipe.foodcontrolback.dao.UsuarioDao;
import com.carlozfelipe.foodcontrolback.entities.Direccion;
import com.carlozfelipe.foodcontrolback.entities.Fidelizacion;
import com.carlozfelipe.foodcontrolback.entities.Usuario;
import com.carlozfelipe.foodcontrolback.enums.UsuarioEnum;
import com.carlozfelipe.foodcontrolback.negocio.util.BCryptEncoderTest;
import com.carlozfelipe.foodcontrolback.negocio.util.TravisAes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioNegocio {
    @Autowired
    UsuarioDao usuarioDao;
    @Autowired
    DireccionDao direccionDao;
    @Autowired
    FidelizacionDao fidelizacionDao;

    public void cear(Direccion direccion){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        if(!direccion.getUsuario().getTipo().equals(UsuarioEnum.CLIENTE)) {
            TravisAes travisAes = new TravisAes();
            String decrypted = travisAes.decrypt(direccion.getUsuario().getPass());
            direccion.getUsuario().setPass(encoder.encode(decrypted));
        }
        if(usuarioDao.findByCelular(direccion.getUsuario().getCelular())!=null){
            throw new IllegalArgumentException("Usuario existemte");
        }
        Usuario usuario = usuarioDao.save(direccion.getUsuario());
        direccion.setUsuario(usuario);
        direccion.setActiva(Boolean.TRUE);
        direccionDao.save(direccion);
        Fidelizacion fidelizacion = new Fidelizacion();
        fidelizacion.setUsuario(usuario);
        fidelizacion.setTotalCompras(0);
        if(usuario.getFechaNacimiento()!=null){
            Calendar cal = Calendar.getInstance();
            int mes = cal.get(Calendar.MONTH);
            int year = cal.get(Calendar.YEAR);
            cal.setTime(usuario.getFechaNacimiento());
            if(mes>=cal.get(Calendar.MONTH)){
                year+=1;
            }
            cal.set(Calendar.YEAR, year);
            fidelizacion.setFechaCumpleanios(cal.getTime());
        }
        fidelizacionDao.save(fidelizacion);
    }

    public Usuario modificar(Usuario usuario) {
        Usuario usuarioModificar = usuarioDao.findById(usuario.getId()).orElse(null);
        if(usuarioModificar!=null){
            return usuarioDao.save(usuario);
        }
        throw new IllegalArgumentException("Unsuario no existe");
    }

    public List<Usuario> obtener() {
        return usuarioDao.findAll();
    }

    public Usuario obtenerId(Long cedula) {
        Usuario usuario = usuarioDao.findById(cedula).orElse(null);
        if(usuario != null){
            return usuario;
        }
        throw new IllegalArgumentException("Unsuario no existe");
    }

    public void eliminar(Long id) {
        Usuario usuario = usuarioDao.findById(id).orElse(null);
        if(usuario!=null){
            usuarioDao.delete(usuario);
        }
    }

    public UsuarioEnum obtenerTipoUsuario(Long cedula) {
        Optional<Usuario> opUsuario = usuarioDao.findById(cedula);
        if(!opUsuario.isPresent()){
            throw new IllegalArgumentException("Usuario no existe");
        }
        return opUsuario.get().getTipo();
    }

    public Usuario update(Usuario usuario) {
        Optional<Usuario> usuarioOptional=usuarioDao.findById(usuario.getId());
        if(!usuarioOptional.isPresent()){
            throw new IllegalArgumentException("Usuario no existe");
        }
        Usuario usuarioBd = usuarioOptional.get();
        if(usuario.getPass()!=null){
            TravisAes travisAes = new TravisAes();
            String decrypted = travisAes.decrypt(usuario.getPass());
            BCryptPasswordEncoder b=new BCryptPasswordEncoder();
            usuarioBd.setPass(b.encode(decrypted));
        }
        usuarioBd.setCelular2(usuario.getCelular2());
        usuarioBd.setNombre(usuario.getNombre());
        usuarioBd.setTipo(usuario.getTipo());
        return usuarioDao.save(usuarioBd);
    }


}
