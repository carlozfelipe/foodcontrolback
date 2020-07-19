package com.carlozfelipe.foodcontrolback.dao;

import com.carlozfelipe.foodcontrolback.entities.Direccion;
import com.carlozfelipe.foodcontrolback.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DireccionDao extends JpaRepository<Direccion, Long> {

    List<Direccion> findByUsuario(Usuario usuario);

    List<Direccion> findByUsuarioAndActivaTrue(Usuario usuario);


}
