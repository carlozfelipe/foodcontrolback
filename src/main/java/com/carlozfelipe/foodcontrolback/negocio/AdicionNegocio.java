package com.carlozfelipe.foodcontrolback.negocio;

import com.carlozfelipe.foodcontrolback.dao.AdicionDao;
import com.carlozfelipe.foodcontrolback.entities.Adicion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdicionNegocio {
    @Autowired
    AdicionDao adicionDao;

    public List<Adicion> obtener(){
        return adicionDao.findAll();
    }
}
