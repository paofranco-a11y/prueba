package com.prueba.ms_pedidos.exception;


// Excepcion personalizada
public class ResourceNotFoundException extends  RuntimeException{
    public ResourceNotFoundException (String message) {
        super(message);
    }
}
