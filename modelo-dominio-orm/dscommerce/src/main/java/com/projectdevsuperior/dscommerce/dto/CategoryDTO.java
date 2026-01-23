package com.projectdevsuperior.dscommerce.dto;

import com.projectdevsuperior.dscommerce.entities.Category;

/**
 * DTO responsável por expor apenas os dados necessários da entidade Category.
 * Evita o acoplamento direto com a camada de persistência.
 */
public class CategoryDTO {

    private Long id;
    private String name;

    /**
     * Construtor utilizado quando os dados já estão disponíveis.
     */
    public CategoryDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * Construtor que converte a entidade Category em DTO.
     */
    public CategoryDTO(Category entity) {
        id = entity.getId();
        name = entity.getName();
    }

    // Getters
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
