package com.carlozfelipe.foodcontrolback.negocio;

import com.carlozfelipe.foodcontrolback.dao.EstiloDao;
import com.carlozfelipe.foodcontrolback.entities.Estilo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EstiloNegocio {
    @Autowired
    EstiloDao estiloDao;

    public Estilo obtener() {
       return  estiloDao.findAll().get(0);
    }

    public void save(Estilo estilo) {
        estiloDao.save(estilo);
    }

    public Estilo modificar(Estilo estilo) {
        Estilo estiloBD = estiloDao.findById(estilo.getId()).orElse(null);
        if(estiloBD != null){
            estiloBD.setColorPrimario(estilo.getColorPrimario());
            estiloBD.setColorSecundario(estilo.getColorSecundario());
            estiloBD.setLogo(estilo.getLogo());
            estiloBD.setNombre(estilo.getNombre());
            estiloBD.setTipoBoton(estilo.getTipoBoton());
            return estiloDao.save(estilo);
        }
        throw new IllegalArgumentException("Estilo no existe");
    }
}
