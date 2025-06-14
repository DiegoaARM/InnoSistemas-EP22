package com.example.InnoSistemas.service;

import com.example.InnoSistemas.entity.PlantillaNotificacion;
import com.example.InnoSistemas.service.PlantillaNotificacionService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class EmailService {

    private final JavaMailSender mailSender;
    private final PlantillaNotificacionService plantillaService;

    public EmailService(JavaMailSender mailSender, PlantillaNotificacionService plantillaService) {
        this.mailSender = mailSender;
        this.plantillaService = plantillaService;
    }

    public void sendNotificationEmail(String to, String tipoNotificacion, Map<String, Object> variables) {
        try {
            // 1. Obtener plantilla desde la base de datos
            PlantillaNotificacion plantilla = plantillaService.findByTipo(tipoNotificacion);

            // 2. Procesar variables en el mensaje
            String mensajeProcesado = procesarVariables(plantilla.getMensajeBase(), variables);
            String tituloProcesado = procesarVariables(plantilla.getTipo(), variables);

            // 3. Crear y enviar email
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(to);
            helper.setSubject(tituloProcesado);
            helper.setText(convertirTextoAHtml(mensajeProcesado), true);

            mailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("Error al enviar email de notificación", e);
        }
    }

    private String procesarVariables(String texto, Map<String, Object> variables) {
        for (Map.Entry<String, Object> entry : variables.entrySet()) {
            texto = texto.replace("${" + entry.getKey() + "}", entry.getValue().toString());
        }
        return texto;
    }

    private String convertirTextoAHtml(String texto) {
        // Convierte saltos de línea a <br> y añade estructura HTML básica
        return "<!DOCTYPE html><html><body style=\"font-family: Arial, sans-serif;\">" +
                texto.replace("\n", "<br>") +
                "</body></html>";
    }
}