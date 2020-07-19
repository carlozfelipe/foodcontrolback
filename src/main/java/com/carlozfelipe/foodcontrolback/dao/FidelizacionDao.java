package com.carlozfelipe.foodcontrolback.dao;

import com.carlozfelipe.foodcontrolback.entities.Fidelizacion;
import com.carlozfelipe.foodcontrolback.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface FidelizacionDao extends JpaRepository<Fidelizacion, Long> {
    Fidelizacion findByUsuario(Usuario usuario);

    List<Usuario> findByTotalComprasGreaterThan(int consumoPremiado);

    List<Usuario> findByfechaCumpleanios(Date diaCumple);
}
