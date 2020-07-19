package com.carlozfelipe.foodcontrolback.negocio;

import com.carlozfelipe.foodcontrolback.dao.FidelizacionDao;
import com.carlozfelipe.foodcontrolback.dao.UsuarioDao;
import com.carlozfelipe.foodcontrolback.entities.Fidelizacion;
import com.carlozfelipe.foodcontrolback.entities.Usuario;
import com.carlozfelipe.foodcontrolback.util.UtilidadFecha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class FidelizacionNegocio {

    private int CONSUMO_PREMIADO = 300000;

    @Autowired
    private FidelizacionDao fidelizacionDao;
    @Autowired
    private UsuarioDao usuarioDao;

    public List<Usuario> obtenerUsuariosPremiados(){
        return fidelizacionDao.findByTotalComprasGreaterThan(CONSUMO_PREMIADO);
    }

    public List<Usuario> obtenerUsuariosCumpleanios() {
        Date diaCumple = UtilidadFecha.hoyAlInicioDelDia();
        return fidelizacionDao.findByfechaCumpleanios(diaCumple);
    }

    public void aplicarPremio(Long idUsuario) {
        Usuario usuario = getUsuario(idUsuario);
        Fidelizacion fidelizacion = fidelizacionDao.findByUsuario(usuario);
        fidelizacion.setTotalCompras(0);
        fidelizacion.setFechaUltimoPedido(UtilidadFecha.hoyAlInicioDelDia());
        fidelizacionDao.save(fidelizacion);
    }

    private Usuario getUsuario(Long idUsuario) {
        Optional<Usuario> usuarioOptional = usuarioDao.findById(idUsuario);
        if(!usuarioOptional.isPresent()){
            throw new IllegalArgumentException("FidelizacionNegocio.aplicarPremio(): Usuario no existe");
        }
        return usuarioOptional.get();
    }

    public void apicarCumple(Long idUsuario) {
        Usuario usuario = getUsuario(idUsuario);
        Fidelizacion fidelizacion = fidelizacionDao.findByUsuario(usuario);
        fidelizacion.setFechaCumpleanios(UtilidadFecha.incrementarAnio(fidelizacion.getFechaCumpleanios()));
        fidelizacionDao.save(fidelizacion);
    }
}
