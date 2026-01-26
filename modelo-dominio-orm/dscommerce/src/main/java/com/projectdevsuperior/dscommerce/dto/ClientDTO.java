package com.projectdevsuperior.dscommerce.dto;

import com.projectdevsuperior.dscommerce.entities.User;

// DTO responsável por expor apenas os dados necessários do cliente
public class ClientDTO {

    private Long id;
    private String name;


    // Construtor utilizado quando os dados já estão disponíveis
    public ClientDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public ClientDTO(User entity) {
        id = entity.getId();
        name = entity.getName();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
