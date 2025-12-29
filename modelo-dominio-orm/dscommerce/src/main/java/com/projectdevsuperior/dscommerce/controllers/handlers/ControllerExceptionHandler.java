package com.projectdevsuperior.dscommerce.controllers.handlers;

import com.projectdevsuperior.dscommerce.dto.CustomError;
import com.projectdevsuperior.dscommerce.services.exceptions.DatabaseException;
import com.projectdevsuperior.dscommerce.services.exceptions.ResourceNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;

@ControllerAdvice
public class ControllerExceptionHandler {

    // Tratamento para erros 404
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<CustomError> resourceNotFound(ResourceNotFoundException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        CustomError err = new CustomError(Instant.now(), status.value(), e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(err);
    }

    // Tratamento para erro de integridade do banco de dados
    @ExceptionHandler(DatabaseException.class)
    public ResponseEntity<CustomError> database(DatabaseException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        CustomError err = new CustomError(Instant.now(), status.value(), e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(err);
    }
}

// Numa classe com a annotation @ControllerAdvice, podemos definir tratamentos globais para
// exceções específicas, sem precisar ficar colocando try-catch em várias partes do código.

/*
{
    "timestamp": "2025-12-29T16:46:22.092596554Z",
    "status": 404,
    "error": "Recurso não encontrado!",
    "path": "/products/100"
}
*/