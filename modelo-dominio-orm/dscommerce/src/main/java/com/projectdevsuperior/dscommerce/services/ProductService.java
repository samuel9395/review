package com.projectdevsuperior.dscommerce.services;

import com.projectdevsuperior.dscommerce.dto.ProductDTO;
import com.projectdevsuperior.dscommerce.entities.Product;
import com.projectdevsuperior.dscommerce.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository repository;

    @Transactional(readOnly = true)                             // Anotação com o argumento de leitura para não dar lock no banco de dados
    public ProductDTO findById(Long id) {
        Optional<Product> result = repository.findById(id);     // Busca no banco de dados o id passado como argumento, e retorna para a variável.
        Product product = result.get();                         // Aqui pegamos o objeto Product que está dentro do Optional usando o método get
        ProductDTO dto = new ProductDTO(product);               // Aqui copiamos os dados do produto(product) para um novo objeto do tipo ProductDTO
        return dto;                                             // Aqui retornamos o objeto
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