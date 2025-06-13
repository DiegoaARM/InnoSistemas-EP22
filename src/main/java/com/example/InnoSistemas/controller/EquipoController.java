package com.example.InnoSistemas.controller;

import com.example.InnoSistemas.entity.Equipo;
import com.example.InnoSistemas.service.EquipoService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class EquipoController {

    private final EquipoService equipoService;

    public EquipoController(EquipoService equipoService) {
        this.equipoService = equipoService;
    }

    @PreAuthorize("isAuthenticated()")
    @QueryMapping
    public List<Equipo> equipos() {
        return equipoService.findAll();
    }

    @PreAuthorize("isAuthenticated()")
    @QueryMapping
    public Equipo equipo(@Argument int id) {
        return equipoService.findById(id);
    }

    @PreAuthorize("isAuthenticated()")
    @QueryMapping
    public List<Equipo> equiposPorCurso(@Argument int cursoId) {
        return equipoService.findByCursoId(cursoId);
    }

    @PreAuthorize("isAuthenticated()")
    @MutationMapping
    public Equipo crearEquipo(@Argument Equipo equipoInput, @Argument int cursoId) {
        Equipo equipo = new Equipo();
        equipo.setNombre(equipoInput.getNombre());
        return equipoService.save(equipo, cursoId);
    }

    @PreAuthorize("isAuthenticated()")
    @MutationMapping
    public Equipo actualizarEquipo(@Argument int id, @Argument Equipo equipoInput) {
        Equipo equipo = new Equipo();
        equipo.setNombre(equipoInput.getNombre());
        return equipoService.update(id, equipo);
    }

    @PreAuthorize("isAuthenticated()")
    @MutationMapping
    public Boolean eliminarEquipo(@Argument int id) {
        equipoService.deleteById(id);
        return true;
    }
}