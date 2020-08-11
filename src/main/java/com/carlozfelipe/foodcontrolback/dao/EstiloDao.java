package com.carlozfelipe.foodcontrolback.dao;

import com.carlozfelipe.foodcontrolback.entities.Estilo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EstiloDao extends JpaRepository<Estilo, Long> {
}
