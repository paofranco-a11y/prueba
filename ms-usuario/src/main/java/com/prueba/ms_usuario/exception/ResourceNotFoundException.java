package com.prueba.ms_usuario.exception;

// Crea una excepción propia y personalizada para usarla cuando busquemos algo en la base de datos y no exista
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String mensaje) {
        super(mensaje);
    }
}