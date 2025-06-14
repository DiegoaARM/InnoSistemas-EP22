package com.example.InnoSistemas.controller;

import com.example.InnoSistemas.entity.Estudiante;
import com.example.InnoSistemas.entity.Integracion;
import com.example.InnoSistemas.service.EstudianteService;
import com.example.InnoSistemas.service.IntegracionService;
import com.example.InnoSistemas.service.NotificacionService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class IntegracionController {

    private final IntegracionService integracionService;

    private final NotificacionService notificacionService;

    private final EstudianteService estudianteService;

    public IntegracionController(IntegracionService integracionService, NotificacionService notificacionService, EstudianteService estudianteService) {
        this.integracionService = integracionService;
        this.notificacionService = notificacionService;
        this.estudianteService = estudianteService;
    }

    @PreAuthorize("isAuthenticated()")
    @QueryMapping
    public List<Integracion> integraciones() {
        return integracionService.findAll();
    }

    @PreAuthorize("isAuthenticated()")
    @QueryMapping
    public Integracion integracion(@Argument int id) {
        return integracionService.findById(id);
    }

    @PreAuthorize("isAuthenticated()")
    @QueryMapping
    public List<Integracion> integracionesPorEstudiante(@Argument int estudianteId) {
        return integracionService.findByEstudianteId(estudianteId);
    }

    @PreAuthorize("isAuthenticated()")
    @QueryMapping
    public List<Integracion> integracionesPorEquipo(@Argument int equipoId) {
        return integracionService.findByEquipoId(equipoId);
    }

    @MutationMapping
    public Integracion crearIntegracion(@Argument int estudianteId, @Argument int equipoId, @Argument int rolId) {
        Integracion integracion = integracionService.save(estudianteId, equipoId, rolId);

        Map<String, Object> variables = new HashMap<>();
        variables.put("rol", integracion.getRol().getNombre());
        variables.put("equipo", integracion.getEquipo().getNombre());

        notificacionService.createFromTemplate(
                "Asignación a equipo",
                estudianteId,
                equipoId,
                variables
        );

        return integracion;
    }

    @MutationMapping
    public Boolean eliminarIntegracion(@Argument int id, @Argument String razon) {
        Integracion integracion = integracionService.findById(id);

        Map<String, Object> variables = new HashMap<>();
        variables.put("razon", razon);
        variables.put("equipo", integracion.getEquipo().getNombre());

        notificacionService.createFromTemplate(
                "Expulsión del equipo",
                integracion.getEstudiante().getId(),
                integracion.getEquipo().getId(),
                variables
        );

        integracionService.delete(id);
        return true;
    }



}
