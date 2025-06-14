package com.example.InnoSistemas.repository;

import com.example.InnoSistemas.entity.PlantillaNotificacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PlantillaNotificacionRepository extends JpaRepository<PlantillaNotificacion, Integer> {
    Optional<PlantillaNotificacion> findByTipo(String tipo);
}
