package com.carlozfelipe.foodcontrolback.rest;

import com.carlozfelipe.foodcontrolback.entities.Reduccion;
import com.carlozfelipe.foodcontrolback.negocio.ReduccionNegocio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reduccion")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET})
public class ReduccionRest {
    @Autowired
    private ReduccionNegocio reduccionNegocio;

    @GetMapping
    public List<Reduccion> obtener(){
        return reduccionNegocio.obtener();
    }
}
