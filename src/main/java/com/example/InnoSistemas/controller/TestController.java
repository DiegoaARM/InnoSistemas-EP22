package com.example.InnoSistemas.controller;

import com.example.InnoSistemas.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private EmailService emailService;

    @GetMapping("/email")
    public String testEmail() {
        try {
            emailService.sendEmail(
                    "juandiegoduquejimenez@gmail.com",
                    "Prueba de correo",
                    "Este es un correo de prueba"
            );
            return "Correo enviado!";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error: " + e.getMessage();
        }
    }
}
