package com.carlozfelipe.foodcontrolback.rest;

import com.carlozfelipe.foodcontrolback.entities.Direccion;
import com.carlozfelipe.foodcontrolback.entities.Usuario;
import com.carlozfelipe.foodcontrolback.enums.UsuarioEnum;
import com.carlozfelipe.foodcontrolback.negocio.UsuarioNegocio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST,RequestMethod.PUT})
public class UsuarioRest {

    @Autowired
    private UsuarioNegocio usuarioNegocio;

    @GetMapping
    public List<Usuario> obtener(){
        return usuarioNegocio.obtener();
    }

    @GetMapping("/{celular}")
    public Usuario obtenerId(@PathVariable Long celular){
        return usuarioNegocio.obtenerId(celular);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void crear(@RequestBody Direccion direccion){
        usuarioNegocio.cear(direccion);
    }

    @PutMapping("/{id}")
    public Usuario modificar(@RequestBody Usuario usuario, @PathVariable Long id){
        return usuarioNegocio.modificar(usuario);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id){
        usuarioNegocio.eliminar(id);
    }

    @GetMapping("/{cedula}/tipo")
    public UsuarioEnum tipoDeUsuario(@PathVariable Long cedula){
        return usuarioNegocio.obtenerTipoUsuario(cedula);
    }

    @PutMapping
    public Usuario update(@RequestBody Usuario usuario){
        return usuarioNegocio.update(usuario);
    }

}
