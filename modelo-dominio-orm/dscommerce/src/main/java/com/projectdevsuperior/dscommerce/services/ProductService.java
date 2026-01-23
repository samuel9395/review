package com.projectdevsuperior.dscommerce.services;

import com.projectdevsuperior.dscommerce.dto.CategoryDTO;
import com.projectdevsuperior.dscommerce.dto.ProductDTO;
import com.projectdevsuperior.dscommerce.dto.ProductMinDTO;
import com.projectdevsuperior.dscommerce.entities.Category;
import com.projectdevsuperior.dscommerce.entities.Product;
import com.projectdevsuperior.dscommerce.repositories.ProductRepository;
import com.projectdevsuperior.dscommerce.services.exceptions.DatabaseException;
import com.projectdevsuperior.dscommerce.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductService {

    // Camada responsável pelas regras de negócio relacionadas a produtos
    @Autowired
    private ProductRepository repository;

    /**
     * Retorna os produtos de forma paginada, com filtro opcional por nome.
     */
    @Transactional(readOnly = true)
    public Page<ProductMinDTO> findAll(String name, Pageable pageable) {

        Page<Product> result = repository.searchByName(name, pageable);

        // Converte cada entidade Product para ProductMinDTO
        return result.map(ProductMinDTO::new);
    }

    /**
     * Busca um produto pelo id.
     * Lança exceção caso o recurso não exista.
     */
    @Transactional(readOnly = true)
    public ProductDTO findById(Long id) {

        Product product = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Recurso não encontrado"));

        return new ProductDTO(product);
    }

    /**
     * Insere um novo produto.
     */
    @Transactional
    public ProductDTO insert(ProductDTO dto) {

        Product entity = new Product();
        copyDtoToEntity(dto, entity);

        entity = repository.save(entity);

        return new ProductDTO(entity);
    }

    /**
     * Atualiza um produto existente.
     */
    @Transactional
    public ProductDTO update(Long id, ProductDTO dto) {
        try {
            Product entity = repository.getReferenceById(id);
            copyDtoToEntity(dto, entity);
            entity = repository.save(entity);
            return new ProductDTO(entity);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Recurso não encontrado");
        }
    }

    /**
     * Remove um produto pelo id.
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    public void delete(Long id) {

        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Recurso não encontrado");
        }

        try {
            repository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Falha de integridade referencial");
        }
    }

    /**
     * Copia os dados do DTO para a entidade Product.
     */
    private void copyDtoToEntity(ProductDTO dto, Product entity) {

        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setPrice(dto.getPrice());
        entity.setImgUrl(dto.getImgUrl());

        // Atualiza o relacionamento com categorias
        entity.getCategories().clear();
        for (CategoryDTO catDto : dto.getCategories()) {
            Category category = new Category();
            category.setId(catDto.getId());
            entity.getCategories().add(category);
        }
    }
}


/*
 * ### PODERIAMOS SIMPLESMENTE RESUMIR O MÉTODO ACIMA ###
 *
 * @Transactional(readOnly = true)
 * public ProductDTO findById(Long id) {
 *    Product product = repository.findById(id).get();
 *    return new ProductDTO(product);
 * }
 * */