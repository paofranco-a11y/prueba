package com.prueba.ms_usuario.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

// Le dice a Spring que esta clase es una "red de seguridad" global que va a atrapar los errores de cualquier controlador
@RestControllerAdvice
public class GlobalExceptionHandler {

    // Si ocurre un error de tipo recurso no encontrado, se activa este método
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<String> manejarNotFound(ResourceNotFoundException ex) {
        // Devuelve un estado 404 (Not Found) junto con el mensaje de error específico
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    // Si los datos que envía el usuario fallan las validaciones de los DTOs (@NotBlank, @Email, etc.), se activa este método
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleValidationErrors(MethodArgumentNotValidException ex) {
        // Junta todos los campos que fallaron y sus mensajes de error en un solo texto separado por comas
        String error = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(e -> e.getField() + ": " + e.getDefaultMessage())
                .collect(Collectors.joining(", "));

        // Devuelve un estado 400 (Bad Request) con la lista de los errores encontrados
        return ResponseEntity.badRequest().body(error);
    }

    // Atrapa cualquier otro error inesperado o genérico en el sistema para que la aplicación no se caiga feo
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGenericError(Exception ex) {
        // Devuelve un estado 500 (Internal Server Error) avisando que hubo un problema interno
        return ResponseEntity.internalServerError()
                .body("Error interno: " + ex.getMessage());
    }
}