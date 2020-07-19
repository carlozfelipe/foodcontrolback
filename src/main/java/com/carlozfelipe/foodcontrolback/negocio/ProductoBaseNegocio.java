package com.carlozfelipe.foodcontrolback.negocio;

import com.carlozfelipe.foodcontrolback.dao.IngredienteBaseDao;
import com.carlozfelipe.foodcontrolback.dao.IngredienteDao;
import com.carlozfelipe.foodcontrolback.dao.ProductoBaseDao;
import com.carlozfelipe.foodcontrolback.entities.Ingrediente;
import com.carlozfelipe.foodcontrolback.entities.IngredienteBase;
import com.carlozfelipe.foodcontrolback.entities.ProductoBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoBaseNegocio {
    @Autowired
    private ProductoBaseDao productoBaseDao;
    @Autowired
    private IngredienteDao ingredienteDao;
    @Autowired
    private IngredienteBaseDao ingredienteBaseDao;

    public void crear(ProductoBase productoBase){
        validarIngredientesBase(productoBase);
        productoBase = productoBaseDao.save(productoBase);
        for(Ingrediente ingrediente:productoBase.getIngredientes()){
            IngredienteBase ingredienteBase = ingredienteBaseDao.findById(ingrediente.getIngredienteBase().getId()).get();
            ingrediente.setIngredienteBase(ingredienteBase);
            ingrediente.setProductoBase(productoBase);
            ingredienteDao.save(ingrediente);
        }
    }

    private void validarIngredientesBase(ProductoBase productoBase) {
        for(Ingrediente ingrediente:productoBase.getIngredientes()){
            if(!ingredienteBaseDao.findById(ingrediente.getIngredienteBase().getId()).isPresent()){
                throw new IllegalArgumentException("No existe ingrediente base");
            }
        }
    }

    public ProductoBase modificar(ProductoBase productoBase) {
        ProductoBase productoBaseModificar = productoBaseDao.findById(productoBase.getId()).orElse(null);
        if(productoBase!=null){
            productoBaseModificar.setNombre(productoBase.getNombre());
            productoBaseModificar.setTamanio(productoBase.getTamanio());
            productoBaseModificar.setTipo(productoBase.getTipo());
            productoBaseModificar.setValor(productoBase.getValor());
            return productoBaseDao.save(productoBase);
        }
        throw new IllegalArgumentException("Ingrediente no existe");
    }

    public List<ProductoBase> obtener() {
        return productoBaseDao.findAll();
    }

    public void eliminar(Long id) {
        ProductoBase productoBase = productoBaseDao.findById(id).orElse(null);
        if(productoBase!=null){
            productoBase.setInactivo(true);
            productoBaseDao.save(productoBase);
        }
    }
}
