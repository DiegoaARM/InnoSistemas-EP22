package com.example.InnoSistemas.controller;

import com.example.InnoSistemas.entity.Notificacion;
import com.example.InnoSistemas.service.NotificacionService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Map;

@Controller
public class NotificacionController {

    private final NotificacionService notificacionService;

    public NotificacionController(NotificacionService notificacionService) {
        this.notificacionService = notificacionService;
    }

    @PreAuthorize("isAuthenticated()")
    @QueryMapping
    public List<Notificacion> notificaciones() {
        return notificacionService.findAll();
    }

    @PreAuthorize("isAuthenticated()")
    @QueryMapping
    public Notificacion notificacion(@Argument int id) {
        return notificacionService.findById(id);
    }

    @PreAuthorize("isAuthenticated()")
    @QueryMapping
    public List<Notificacion> notificacionesPorEstudiante(@Argument int estudianteId) {
        return notificacionService.findByEstudianteId(estudianteId);
    }

    @PreAuthorize("isAuthenticated()")
    @QueryMapping
    public List<Notificacion> notificacionesPorEquipo(@Argument int equipoId) {
        return notificacionService.findByEquipoId(equipoId);
    }

    @PreAuthorize("isAuthenticated()")
    @MutationMapping
    public Notificacion crearNotificacion(
            @Argument String plantilla,
            @Argument int estudianteId,
            @Argument int equipoId) {
        return notificacionService.createFromTemplate(plantilla, estudianteId, equipoId);
    }

    @PreAuthorize("isAuthenticated()")
    @MutationMapping
    public Notificacion marcarNotificacionComoLeida(@Argument int id) {
        return notificacionService.marcarComoLeida(id);
    }

    @PreAuthorize("isAuthenticated()")
    @MutationMapping
    public Boolean eliminarNotificacion(@Argument int id) {
        notificacionService.delete(id);
        return true;
    }
}
