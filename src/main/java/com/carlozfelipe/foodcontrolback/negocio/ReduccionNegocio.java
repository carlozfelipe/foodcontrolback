package com.carlozfelipe.foodcontrolback.negocio;

import com.carlozfelipe.foodcontrolback.dao.ReduccionDao;
import com.carlozfelipe.foodcontrolback.entities.Reduccion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReduccionNegocio {
    @Autowired
    ReduccionDao reduccionDao;

    public List<Reduccion> obtener(){
        return reduccionDao.findAll();
    }
}
