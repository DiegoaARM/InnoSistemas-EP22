package com.example.InnoSistemas.controller;

import com.example.InnoSistemas.entity.Curso;
import com.example.InnoSistemas.entity.Estudiante;
import com.example.InnoSistemas.service.CursoService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class CursoController {

    private final CursoService cursoService;

    public CursoController(CursoService cursoService) {
        this.cursoService = cursoService;
    }

    @PreAuthorize("isAuthenticated()")
    @QueryMapping
    public List<Curso> cursos() {
        return cursoService.findAll();
    }

    @PreAuthorize("isAuthenticated()")
    @QueryMapping
    public Curso curso(@Argument int id) {
        return cursoService.findById(id);
    }

    @PreAuthorize("isAuthenticated()")
    @MutationMapping
    public Curso crearCurso(@Argument Curso curso) {
        // Crear nuevo curso con solo los campos básicos
        Curso nuevoCurso = new Curso();
        nuevoCurso.setNombre(curso.getNombre());
        nuevoCurso.setSemestre(curso.getSemestre());

        // Guardar el curso primero
        Curso cursoGuardado = cursoService.save(nuevoCurso);

        // Asignar estudiantes si existen
        if(curso.getEstudianteList() != null && !curso.getEstudianteList().isEmpty()) {
            for(Estudiante estudiante : curso.getEstudianteList()) {
                cursoService.addEstudianteToCurso(cursoGuardado.getId(), estudiante.getId());
            }
        }

        // 4. Obtener el curso COMPLETO con estudiantes cargados
        return cursoService.findById(cursoGuardado.getId());
    }

    @PreAuthorize("isAuthenticated()")
    @MutationMapping
    public Curso actualizarCurso(@Argument int id, @Argument Curso input) {
        Curso curso = new Curso();
        curso.setNombre(input.getNombre());
        curso.setSemestre(input.getSemestre());
        return cursoService.update(id, curso);
    }

    @PreAuthorize("isAuthenticated()")
    @MutationMapping
    public Boolean eliminarCurso(@Argument int id) {
        cursoService.deleteById(id);
        return true;
    }

    @PreAuthorize("isAuthenticated()")
    @QueryMapping
    public List<Estudiante> estudiantesPorCurso(@Argument int id) {
        return cursoService.getEstudiantesByCursoId(id);
    }

    @PreAuthorize("isAuthenticated()")
    @MutationMapping
    public Boolean agregarEstudianteACurso(@Argument int cursoId, @Argument int estudianteId) {
        cursoService.addEstudianteToCurso(cursoId, estudianteId);
        return true;
    }

    @PreAuthorize("isAuthenticated()")
    @MutationMapping
    public Boolean eliminarEstudianteDeCurso(@Argument int cursoId, @Argument int estudianteId) {
        cursoService.deleteEstudianteFromCurso(cursoId, estudianteId);
        return true;
    }
}
