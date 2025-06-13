package com.example.InnoSistemas.controller;

import com.example.InnoSistemas.entity.Integracion;
import com.example.InnoSistemas.service.IntegracionService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class IntegracionController {

    private final IntegracionService integracionService;

    public IntegracionController(IntegracionService integracionService) {
        this.integracionService = integracionService;
    }

    @QueryMapping
    public List<Integracion> integraciones() {
        return integracionService.findAll();
    }

    @QueryMapping
    public Integracion integracion(@Argument int id) {
        return integracionService.findById(id);
    }

    @QueryMapping
    public List<Integracion> integracionesPorEstudiante(@Argument int estudianteId) {
        return integracionService.findByEstudianteId(estudianteId);
    }

    @QueryMapping
    public List<Integracion> integracionesPorEquipo(@Argument int equipoId) {
        return integracionService.findByEquipoId(equipoId);
    }

    @MutationMapping
    public Integracion crearIntegracion(@Argument int estudianteId, @Argument int equipoId, @Argument int rolId) {
        return integracionService.save(estudianteId, equipoId, rolId);
    }

    @MutationMapping
    public Boolean eliminarIntegracion(@Argument int id, @Argument int currentlyEstudianteId) {
        // Buscar la integración a eliminar
        Integracion integracion = integracionService.findById(id);
        if (integracion == null) {
            throw new RuntimeException("La integración no existe.");
        }
        // Obtener el equipo al que pertenece esta integración
        int equipoId = integracion.getEquipo().getId();
        // Buscar si el estudiante actual tiene rol de administrador en este equipo
        List<Integracion> integracionesDelEquipo = integracionService.findByEquipoId(equipoId);

        boolean esAdmin = integracionesDelEquipo.stream()
                .anyMatch(i -> i.getEstudiante().getId() == currentlyEstudianteId
                        && i.getRol().getId() == 1);
        if (!esAdmin) {
            throw new RuntimeException("No tienes permisos para eliminar esta integración.");
        }
        // Si es administrador, proceder a eliminar
        integracionService.delete(id);
        return true;
    }

}
