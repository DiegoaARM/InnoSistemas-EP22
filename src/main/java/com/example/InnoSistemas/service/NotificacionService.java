package com.example.InnoSistemas.service;

import com.example.InnoSistemas.entity.Equipo;
import com.example.InnoSistemas.entity.Estudiante;
import com.example.InnoSistemas.entity.Notificacion;
import com.example.InnoSistemas.entity.PlantillaNotificacion;
import com.example.InnoSistemas.repository.EquipoRepository;
import com.example.InnoSistemas.repository.EstudianteRepository;
import com.example.InnoSistemas.repository.NotificacionRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@Service
public class NotificacionService {

    private final EmailService emailService;
    private final PlantillaNotificacionService plantillaService;
    private final EstudianteRepository estudianteRepository;
    private final EquipoRepository equipoRepository;
    private final NotificacionRepository notificacionRepository;



    public NotificacionService(EmailService emailService, PlantillaNotificacionService plantillaService,
                               EstudianteRepository estudianteRepository, EquipoRepository equipoRepository,
                               NotificacionRepository notificacionRepository) {
        this.emailService = emailService;
        this.plantillaService = plantillaService;
        this.estudianteRepository = estudianteRepository;
        this.equipoRepository = equipoRepository;
        this.notificacionRepository = notificacionRepository;
    }

    public List<Notificacion> findAll() {
        return notificacionRepository.findAll();
    }

    public Notificacion findById(Integer id) {
        return notificacionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Notificación no encontrada"));
    }

    public Notificacion createFromTemplate(String tipo, Integer estudianteId, Integer equipoId) {
        // 1. Obtener estudiante y equipo
        Estudiante estudiante = estudianteRepository.findById(estudianteId)
                .orElseThrow(() -> new EntityNotFoundException("Estudiante no encontrado"));
        Equipo equipo = equipoRepository.findById(equipoId)
                .orElseThrow(() -> new EntityNotFoundException("Equipo no encontrado"));

        // 2. Crear notificación en base de datos
        Notificacion notificacion = new Notificacion();
        PlantillaNotificacion plantilla = plantillaService.findByTipo(tipo);
        notificacion.setPlantilla(plantilla);
        notificacion.setEstudiante(estudiante);
        notificacion.setEquipo(equipo);
        notificacion.setFecha_envio(LocalDateTime.now());
        notificacion.setLeida(false);

        System.out.println(estudiante.getEmail());
        emailService.sendEmail(estudiante.getEmail(), plantilla.getTipo(), plantilla.getMensajeBase());

        return notificacionRepository.save(notificacion);
    }

    public Notificacion marcarComoLeida(Integer id) {
        Notificacion notificacion = findById(id);
        notificacion.setLeida(true);
        return notificacionRepository.save(notificacion);
    }

    public void delete(Integer id) {
        notificacionRepository.deleteById(id);
    }

    public List<Notificacion> findByEstudianteId(Integer estudianteId) {
        Estudiante estudiante = estudianteRepository.findById(estudianteId)
                .orElseThrow(() -> new EntityNotFoundException("Estudiante no encontrado"));
        return notificacionRepository.findByEstudiante(estudiante);
    }

    public List<Notificacion> findByEquipoId(Integer equipoId) {
        Equipo equipo = equipoRepository.findById(equipoId)
                .orElseThrow(() -> new EntityNotFoundException("Equipo no encontrado"));
        return notificacionRepository.findByEquipo(equipo);
    }
}
