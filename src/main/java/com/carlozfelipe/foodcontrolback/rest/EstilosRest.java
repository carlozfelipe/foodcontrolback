package com.carlozfelipe.foodcontrolback.rest;

import com.carlozfelipe.foodcontrolback.entities.Estilo;
import com.carlozfelipe.foodcontrolback.negocio.EstiloNegocio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/estilos")
public class EstilosRest {
    @Autowired
    EstiloNegocio estiloNegocio;

    @GetMapping
    public Estilo obtener(){
        return estiloNegocio.obtener();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void crear(@RequestBody Estilo estilo){
        estiloNegocio.save(estilo);
    }

    @PutMapping
    public Estilo modificar(@RequestBody Estilo estilo){
        return estiloNegocio.modificar(estilo);
    }
}
