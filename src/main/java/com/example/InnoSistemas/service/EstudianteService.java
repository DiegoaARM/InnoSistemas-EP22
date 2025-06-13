package com.example.InnoSistemas.service;

import com.example.InnoSistemas.entity.Curso;
import com.example.InnoSistemas.entity.Estudiante;
import com.example.InnoSistemas.repository.EstudianteRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EstudianteService {

    @Autowired
    private EstudianteRepository estudianteRepository;

    public List<Estudiante> findAll() {
        return estudianteRepository.findAll();
    }

    public Estudiante findById(int id) {
        return estudianteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Estudiante no encontrado"));
    }

    public Estudiante save(Estudiante estudiante) {
        return estudianteRepository.save(estudiante);
    }

    public Estudiante update(Estudiante estudiante) {
        return estudianteRepository.save(estudiante);
    }

    // Operaciones específicas para la relación con cursos
    public List<Curso> getCursosByEstudianteId(Integer estudianteId) {
        Estudiante estudiante = estudianteRepository.findByIdWithCursos(estudianteId)
                .orElseThrow(() -> new EntityNotFoundException("Estudiante no encontrado"));
        return estudiante.getCursoList();
    }

    public Optional<Estudiante> findByEmail(String email) {
        return estudianteRepository.findByEmail(email);
    }
}
