package com.projectdevsuperior.dscommerce.services;

import com.projectdevsuperior.dscommerce.dto.ProductDTO;
import com.projectdevsuperior.dscommerce.entities.Product;
import com.projectdevsuperior.dscommerce.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository repository;                      // A camada de serviço depende da camada de acesso a dados (Repository)

    @Transactional(readOnly = true)                            // readOnly = true evita lock desnecessário no banco
    public ProductDTO findById(Long id) {                      // Método responsável por buscar um produto pelo id
        Optional<Product> result = repository.findById(id);    // Busca no banco de dados o id passado como argumento, e retorna para a variável.
        Product product = result.get();                        // Aqui pegamos o objeto Product que está dentro do Optional usando o método get
        ProductDTO dto = new ProductDTO(product);              // Aqui copiamos os dados do produto(product) para um novo objeto do tipo ProductDTO
        return dto;                                            // Retorna o DTO para o controller
    }

    @Transactional(readOnly = true)
    public Page<ProductDTO> findAll(Pageable pageable) {       // Método responsável por buscar todos os produtos de forma paginada
        Page<Product> result = repository.findAll(pageable);   // Aqui mudamos para o Page, para fazer a busca paginada
        return result.map(x -> new ProductDTO(x));     // Converte cada Product em ProductDTO e retorna como lista
    }

    @Transactional
    public ProductDTO insert(ProductDTO dto) {

        Product entity = new Product();
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setPrice(dto.getPrice());
        entity.setImgUrl(dto.getImgUrl());

        entity = repository.save(entity);

        return new ProductDTO(entity);
    }
}

/*
* ### PODERIAMOS SIMPLESMENTE RESUMIR O MÉTODO ACIMA ###
*
* @Transactional(readOnly = true)
* public ProductDTO findById(Long id) {
*    Product product = repository.findById(id).get();
*    return new ProductDTO(product);
 * */