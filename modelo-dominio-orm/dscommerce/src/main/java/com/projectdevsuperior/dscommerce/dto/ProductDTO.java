package com.projectdevsuperior.dscommerce.dto;

import com.projectdevsuperior.dscommerce.entities.Category;
import com.projectdevsuperior.dscommerce.entities.Product;
import jakarta.validation.constraints.*;

import java.util.ArrayList;
import java.util.List;

/**
 * DTO responsável por representar os dados de Product
 * utilizados nas operações de entrada e saída da API.
 */
public class ProductDTO {

    private Long id;

    /**
     * Nome do produto.
     * Não pode ser vazio e deve ter entre 3 e 80 caracteres.
     */
    @NotBlank(message = "Campo requerido")
    @Size(min = 3, max = 80, message = "Requerido de 3 a 80 caracteres")
    private String name;

    /**
     * Descrição do produto.
     * Obrigatória e com no mínimo 10 caracteres.
     */
    @NotBlank(message = "Campo requerido")
    @Size(min = 10, message = "Requerido no mínimo 10 caracteres")
    private String description;

    /**
     * Preço do produto.
     * Deve ser um valor positivo.
     * Não pode ser null
     */
    @NotNull(message = "Campo requerido")
    @Positive(message = "O preço deve ser positivo")
    private Double price;

    private String imgUrl;

    /**
     * Categorias associadas ao produto.
     * Deve conter ao menos uma categoria.
     */
    @NotEmpty(message = "Deve ter pelo menos uma categoria")
    private List<CategoryDTO> categories = new ArrayList<>();

    public ProductDTO() {
    }

    /**
     * Construtor utilizado quando os dados já estão disponíveis.
     */
    public ProductDTO(Long id, String name, String description, Double price, String imgUrl) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.imgUrl = imgUrl;
    }

    /**
     * Construtor que converte a entidade Product em DTO.
     */
    public ProductDTO(Product entity) {
        id = entity.getId();
        name = entity.getName();
        description = entity.getDescription();
        price = entity.getPrice();
        imgUrl = entity.getImgUrl();

        // Converte as categorias da entidade para DTO
        for (Category category : entity.getCategories()) {
            categories.add(new CategoryDTO(category));
        }
    }

    // Getters
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

    public List<CategoryDTO> getCategories() {
        return categories;
    }
}
