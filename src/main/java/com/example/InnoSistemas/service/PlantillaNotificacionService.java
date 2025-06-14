package com.example.InnoSistemas.service;

import com.example.InnoSistemas.entity.PlantillaNotificacion;
import com.example.InnoSistemas.repository.PlantillaNotificacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlantillaNotificacionService {

    @Autowired
    private PlantillaNotificacionRepository plantillaRepository;

    public List<PlantillaNotificacion> findAll() {
        return plantillaRepository.findAll();
    }

    public PlantillaNotificacion findByTipo(String tipo) {
        return plantillaRepository.findByTipo(tipo)
                .orElseThrow(() -> new RuntimeException("Plantilla no encontrada para tipo: " + tipo));
    }

    public PlantillaNotificacion save(PlantillaNotificacion plantilla) {
        return plantillaRepository.save(plantilla);
    }
}