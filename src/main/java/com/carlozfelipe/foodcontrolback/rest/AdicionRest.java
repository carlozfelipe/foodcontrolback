package com.carlozfelipe.foodcontrolback.rest;

import com.carlozfelipe.foodcontrolback.entities.Adicion;
import com.carlozfelipe.foodcontrolback.negocio.AdicionNegocio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/adicion")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET})
public class AdicionRest {
    @Autowired
    private AdicionNegocio adicionNegocio;

    @GetMapping
    public List<Adicion> obtener(){
        return adicionNegocio.obtener();
    }
}
