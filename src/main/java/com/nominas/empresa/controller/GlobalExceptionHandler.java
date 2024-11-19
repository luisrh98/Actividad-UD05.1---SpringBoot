package com.nominas.empresa.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public String handleException(Exception e) {
        return "error"; // Nombre de la vista 'error.html'
    }

    @RequestMapping("/error")
    public String handleError() {
        // Puedes agregar más lógica para manejar diferentes códigos de error
        return "error"; // Vista para el error
    }
}