package com.example.InnoSistemas.service;

import com.example.InnoSistemas.entity.Estudiante;
import com.example.InnoSistemas.repository.EstudianteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.Collections;

@Service
public class EstudianteDetailsService implements UserDetailsService {

    @Autowired
    private EstudianteRepository estudianteRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Estudiante estudiante = estudianteRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("No se encontró el estudiante con email: " + email));

        return new org.springframework.security.core.userdetails.User(
                estudiante.getEmail(),
                estudiante.getPassword(),
                Collections.emptyList() // sin roles por ahora
        );
    }
}

