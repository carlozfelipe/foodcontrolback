package com.carlozfelipe.foodcontrolback.rest;

import com.carlozfelipe.foodcontrolback.entities.IngredienteBase;
import com.carlozfelipe.foodcontrolback.negocio.IngredienteBaseNegocio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ingredientesbase")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST})
public class IngredienteBaseRest {

    @Autowired
    private IngredienteBaseNegocio ingredienteBaseNegocio;

    @GetMapping
    public List<IngredienteBase> obtener(){
        return ingredienteBaseNegocio.obtener();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void crear(@RequestBody IngredienteBase ingredienteBase){
        ingredienteBaseNegocio.crear(ingredienteBase);
    }

    @PutMapping("/{id}")
    public IngredienteBase modificar(@RequestBody IngredienteBase ingredienteBase, @PathVariable Long id){
        return ingredienteBaseNegocio.modificar(ingredienteBase);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id){
        ingredienteBaseNegocio.eliminar(id);
    }
}
