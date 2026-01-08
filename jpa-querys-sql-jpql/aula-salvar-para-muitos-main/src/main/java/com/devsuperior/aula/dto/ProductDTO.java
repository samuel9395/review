package com.devsuperior.aula.dto;

import com.devsuperior.aula.entities.Category;
import com.devsuperior.aula.entities.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductDTO {

    private Long id;
    private String name;
    private Double price;

    private List<CategoryDTO> categories = new ArrayList<>();

    public ProductDTO() {
    }

    public ProductDTO(Long id, String name, Double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    // Aqui instanciamos o ProductDTO com as categorias aninhadas á ele
    public ProductDTO(Product entity) {
        id = entity.getId();
        name = entity.getName();
        price = entity.getPrice();

        for (Category category : entity.getCategories()) {
            categories.add(new CategoryDTO(category));
        }
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

    public List<CategoryDTO> getCategories() {
        return categories;
    }
}
