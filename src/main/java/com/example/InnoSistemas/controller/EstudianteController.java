package com.example.InnoSistemas.controller;

import com.example.InnoSistemas.entity.Curso;
import com.example.InnoSistemas.entity.Estudiante;
import com.example.InnoSistemas.login.AuthRequest;
import com.example.InnoSistemas.security.JwtUtil;
import com.example.InnoSistemas.service.EstudianteService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class EstudianteController {

    private final EstudianteService estudianteService;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;


    public EstudianteController(EstudianteService estudianteService, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.estudianteService = estudianteService;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    @QueryMapping
    public List<Estudiante> estudiantes() {
        return estudianteService.findAll();
    }

    @QueryMapping
    public Estudiante estudiante(@Argument int id) {
        return estudianteService.findById(id);
    }

    @QueryMapping
    public List<Curso> cursosPorEstudiante(@Argument int estudianteId) {
        return estudianteService.getCursosByEstudianteId(estudianteId);
    }

    @MutationMapping
    public Estudiante crearEstudiante(@Argument Estudiante input) {
        Estudiante estudiante = new Estudiante();
        estudiante.setNombre(input.getNombre());
        estudiante.setEmail(input.getEmail());
        estudiante.setApellidos(input.getApellidos());

        // Encriptamos la contraseña antes de guardar
        String hashedPassword = passwordEncoder.encode(input.getPassword());
        estudiante.setPassword(hashedPassword);

        return estudianteService.save(estudiante);
    }


    @MutationMapping
    public Estudiante actualizarEstudiante(@Argument Estudiante input) {
        Estudiante estudiante = estudianteService.findById(input.getId());
        estudiante.setNombre(input.getNombre());
        estudiante.setEmail(input.getEmail());
        estudiante.setApellidos(input.getApellidos());

        if (input.getPassword() != null && !input.getPassword().isEmpty()) {
            String hashedPassword = passwordEncoder.encode(input.getPassword());
            estudiante.setPassword(hashedPassword);
        }

        return estudianteService.update(estudiante);
    }


    @MutationMapping
    public String login(@Argument AuthRequest input) {
        Optional<Estudiante> optionalEstudiante = estudianteService.findByEmail(input.getEmail());

        Estudiante estudiante = optionalEstudiante.orElseThrow(() ->
                new RuntimeException("Usuario no encontrado")
        );

        if (!estudiante.getPassword().trim().equals(input.getPassword().trim())) {
            throw new RuntimeException("Contraseña inválida");
        }



        String token = jwtUtil.generateToken(estudiante.getEmail());
        return token;
    }
}
