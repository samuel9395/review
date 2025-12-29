package com.projectdevsuperior.dscommerce.dto;

import com.projectdevsuperior.dscommerce.entities.Product;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public class ProductDTO {

    private Long id;

    // Anotação para verificar se o campo do nome não está vazio
    @NotBlank(message = "Campo requerido")
    @Size(min = 3, max = 80, message = "Requerido de 3 a 80 caractéres")
    private String name;

    @NotBlank(message = "Campo requerido")
    @Size(min = 10, message = "Requerido no mínimo 10 caractéres")
    private String description;

    @Positive(message = "O preço deve ser positivo")
    private Double price;
    private String imgUrl;

    public ProductDTO() {}

    public ProductDTO(Long id, String name, String description, Double price, String imgUrl) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.imgUrl = imgUrl;
    }

    // Construtor que recebe a entidade Product
    // Usado na camada de serviço para converter Entity -> DTO
    public ProductDTO(Product entity) {
        id = entity.getId();
        name = entity.getName();
        description = entity.getDescription();
        price = entity.getPrice();
        imgUrl = entity.getImgUrl();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Double getPrice() {
        return price;
    }

    public String getImgUrl() {
        return imgUrl;
    }
}
