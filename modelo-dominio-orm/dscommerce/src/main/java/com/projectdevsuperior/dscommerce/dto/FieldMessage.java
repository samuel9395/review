package com.projectdevsuperior.dscommerce.dto;

public class FieldMessage {

    private String fieldName;
    private String message;

    public FieldMessage(String fieldName, String message) {
        this.fieldName = fieldName;
        this.message = message;
    }

    public String getFieldName() {
        return fieldName;
    }

    public String getMessage() {
        return message;
    }
}

// Classe criada para lançar exceção personalizada para argumentos inválidos,
// tipo campo requerido não preenchido, lançando a mensagem de erro.