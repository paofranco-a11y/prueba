package com.prueba.ms_inventario.exception;

public class ResourceNotFoundException extends  RuntimeException{
    public ResourceNotFoundException (String message) {
        super(message);
    }
}
