package com.carlozfelipe.foodcontrolback.negocio;

import com.carlozfelipe.foodcontrolback.dao.AdicionDao;
import com.carlozfelipe.foodcontrolback.dao.IngredienteBaseDao;
import com.carlozfelipe.foodcontrolback.dao.ReduccionDao;
import com.carlozfelipe.foodcontrolback.entities.Adicion;
import com.carlozfelipe.foodcontrolback.entities.IngredienteBase;
import com.carlozfelipe.foodcontrolback.entities.Reduccion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IngredienteBaseNegocio {
    @Autowired
    IngredienteBaseDao ingredienteBaseDao;
    @Autowired
    AdicionDao adicionDao;
    @Autowired
    ReduccionDao reduccionDao;

    public void crear(IngredienteBase ingredienteBase){
        ingredienteBase = ingredienteBaseDao.save(ingredienteBase);
        if(ingredienteBase.getValorAdicion()!=null){
            Adicion adicion = new Adicion();
            adicion.setIngredienteBase(ingredienteBase);
            adicion.setValor(ingredienteBase.getValorAdicion());
            adicionDao.save(adicion);
        }
        if(ingredienteBase.getValorReduccion()!=null){
            Reduccion reduccion = new Reduccion();
            reduccion.setIngredienteBase(ingredienteBase);
            reduccion.setValor(ingredienteBase.getValorReduccion());
            reduccionDao.save(reduccion);
        }
    }

    public IngredienteBase modificar(IngredienteBase ingredienteBase) {
        IngredienteBase ingredienteBaseModificar = ingredienteBaseDao.findById(ingredienteBase.getId()).orElse(null);
        if(ingredienteBase!=null){
            return ingredienteBaseDao.save(ingredienteBase);
        }
        throw new IllegalArgumentException("Ingrediente no existe");
    }

    public List<IngredienteBase> obtener() {
        return ingredienteBaseDao.findAll();
    }

    public void eliminar(Long id) {
        IngredienteBase ingredienteBase = ingredienteBaseDao.findById(id).orElse(null);
        if(ingredienteBase!=null){
            ingredienteBase.setInactivo(true);
            ingredienteBaseDao.save(ingredienteBase);
        }
    }
}
