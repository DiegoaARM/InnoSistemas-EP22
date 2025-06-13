package com.example.InnoSistemas.login;

public class AuthResponse {
    private String jwt;
    private String message;
    private boolean success;

    public AuthResponse(String jwt) {
        this.jwt = jwt;
        this.message = "Login exitoso";
        this.success = true;
    }

    public AuthResponse(String message, boolean success) {
        this.message = message;
        this.success = success;
    }

    public AuthResponse() {
    }

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}

