package com.carlozfelipe.foodcontrolback.rest;

import com.carlozfelipe.foodcontrolback.entities.ProductoBase;
import com.carlozfelipe.foodcontrolback.negocio.ProductoBaseNegocio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/productobase")
@CrossOrigin(origins = "*", methods = {RequestMethod.POST, RequestMethod.GET})
public class ProductoBaseRest {

    @Autowired
    private ProductoBaseNegocio productoBaseNegocio;

    @GetMapping
    public List<ProductoBase> obtener(){
        return productoBaseNegocio.obtener();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void crear(@RequestBody ProductoBase productoBase){
        productoBaseNegocio.crear(productoBase);
    }

    @PutMapping("/{id}")
    public ProductoBase modificar(@RequestBody ProductoBase productoBase, @PathVariable Long id){
        return productoBaseNegocio.modificar(productoBase);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id){
        productoBaseNegocio.eliminar(id);
    }
}
