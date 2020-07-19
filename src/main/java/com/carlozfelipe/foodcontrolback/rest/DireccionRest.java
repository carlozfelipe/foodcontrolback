package com.carlozfelipe.foodcontrolback.rest;

import com.carlozfelipe.foodcontrolback.dao.UsuarioDao;
import com.carlozfelipe.foodcontrolback.entities.Direccion;
import com.carlozfelipe.foodcontrolback.entities.Usuario;
import com.carlozfelipe.foodcontrolback.negocio.DireccionNegocio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/direccion")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT})
public class DireccionRest {

    @Autowired
    private DireccionNegocio direccionNegocio;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Direccion add(@RequestBody Direccion direccion){
        return direccionNegocio.save(direccion);
    }

    @GetMapping("/{celular}")
    public List<Direccion> obtener(@PathVariable Long celular){
        return direccionNegocio.obtenerPorCedula(celular);
    }

    @PutMapping
    public Direccion update(@RequestBody Direccion direccion){
        return direccionNegocio.update(direccion);
    }

    @DeleteMapping("/{direccionId}")
    public void delete(@PathVariable Long direccionId){
        direccionNegocio.delete(direccionId);
    }
}
