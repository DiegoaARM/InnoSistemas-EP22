package com.example.InnoSistemas.repository;

import com.example.InnoSistemas.entity.Estudiante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EstudianteRepository extends JpaRepository<Estudiante, Integer> {

    // Obtener estudiante con cursos (carga eager personalizada)
    @Query("SELECT e FROM Estudiante e JOIN FETCH e.cursoList WHERE e.id = :id")
    Optional<Estudiante> findByIdWithCursos(@Param("id") Integer id);

    Optional<Estudiante> findByEmail(String email);
}
