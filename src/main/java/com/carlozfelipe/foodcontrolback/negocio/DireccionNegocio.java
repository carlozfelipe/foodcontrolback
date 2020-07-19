package com.carlozfelipe.foodcontrolback.negocio;

import com.carlozfelipe.foodcontrolback.dao.DireccionDao;
import com.carlozfelipe.foodcontrolback.dao.UsuarioDao;
import com.carlozfelipe.foodcontrolback.entities.Direccion;
import com.carlozfelipe.foodcontrolback.entities.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DireccionNegocio {
    @Autowired
    DireccionDao direccionDao;
    @Autowired
    UsuarioDao usuarioDao;

    public Direccion save(Direccion direccion){
        Usuario usuario = usuarioDao.findById(direccion.getUsuario().getId()).orElse(null);
        validarUsuario(usuario);
        direccion.setUsuario(usuario);
        direccion.setActiva(Boolean.TRUE);
        return direccionDao.save(direccion);
    }

    public List<Direccion> obtenerPorCedula(Long cedula) {
        Usuario usuario = usuarioDao.findById(cedula).orElse(null);
        validarUsuario(usuario);
        return direccionDao.findByUsuarioAndActivaTrue(usuario);
    }

    private void validarUsuario(Usuario usuario){
        if(usuario == null){
            throw  new IllegalArgumentException("No existe usuario con id: " + usuario.getId());
        }
    }

    public Direccion update(Direccion direccion) {
        Optional<Direccion> direccionOptional = direccionDao.findById(direccion.getId());
        if(!direccionOptional.isPresent()){
            throw  new IllegalArgumentException("No existe dirección con id: " + direccion.getId());
        }
        Direccion direccionBD = direccionOptional.get();
        direccionBD.setBarrio(direccion.getBarrio());
        direccionBD.setNumeroCasa(direccion.getNumeroCasa());
        direccionBD.setNumeroVia(direccion.getNumeroVia());
        direccionBD.setReferencia(direccion.getReferencia());
        direccionBD.setReferencia(direccion.getReferencia());
        direccionBD.setActiva(direccion.getActiva());
        return direccionDao.save(direccionBD);
    }


    public void delete(Long direccionId) {
        Optional<Direccion> direccionOptional = direccionDao.findById(direccionId);
        if(!direccionOptional.isPresent()){
            throw  new IllegalArgumentException("No existe dirección con id: " + direccionId);
        }
        Direccion direccionBD = direccionOptional.get();
        direccionBD.setActiva(Boolean.FALSE);
        direccionDao.save(direccionBD);
    }


}

