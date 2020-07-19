package com.carlozfelipe.foodcontrolback.rest;

import com.carlozfelipe.foodcontrolback.entities.Usuario;
import com.carlozfelipe.foodcontrolback.negocio.FidelizacionNegocio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/fidelizacion")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.PUT})
public class FidelizacionRest {
    @Autowired
    private FidelizacionNegocio fidelizacionNegocio;

    @GetMapping("/usuarios/premiados")
    public List<Usuario> obtenerUsuariosPremiados(){
        return fidelizacionNegocio.obtenerUsuariosPremiados();
    }

    @GetMapping("/usuarios/cumpleanios")
    public List<Usuario> obtenerUsuariosCumpleanios(){
        return fidelizacionNegocio.obtenerUsuariosCumpleanios();
    }

    @PutMapping("/usuario/{idUsuario}/aplicarPremio")
    public void aplicarPremio(@PathVariable Long idUsuario){
        fidelizacionNegocio.aplicarPremio(idUsuario);
    }

    @PutMapping("/usuario/{idUsuario}/aplicarCumple")
    public void aplicarCumple(@PathVariable Long idUsuario){
        fidelizacionNegocio.apicarCumple(idUsuario);
    }
}
