package com.projectdevsuperior.dscommerce.services.exceptions;

// Exceção customizada, onde para responder pelo try catch é necessário extendê-la.
// Não sendo necessário utilizar o try catch na compilação.
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {
        super(message);
    }
}
