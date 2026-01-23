package com.projectdevsuperior.dscommerce.dto;

import com.projectdevsuperior.dscommerce.entities.Product;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public class ProductMinDTO {

    private Long id;

    private String name;
    private String description;
    private Double price;
    private String imgUrl;

    public ProductMinDTO() {
    }

    public ProductMinDTO(Long id, String name, Double price, String imgUrl) {
        this.id = id;
        this.name = name;

        this.price = price;
        this.imgUrl = imgUrl;
    }

    // Construtor que recebe a entidade Product
    // Usado na camada de serviço para converter Entity -> DTO
    public ProductMinDTO(Product entity) {
        id = entity.getId();
        name = entity.getName();

        price = entity.getPrice();
        imgUrl = entity.getImgUrl();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Double getPrice() {
        return price;
    }

    public String getImgUrl() {
        return imgUrl;
    }
}
