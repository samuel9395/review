package com.projectdevsuperior.dscommerce.controllers;

import com.projectdevsuperior.dscommerce.dto.ProductDTO;
import com.projectdevsuperior.dscommerce.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/products")
public class ProductController {

    // O controller não acessa o banco de dados diretamente
    // Ele delega a lógica para a camada de serviço
    @Autowired
    private ProductService service;

    // Endpoint para buscar um produto pelo id
    // Exemplo: GET /products/1
    @GetMapping(value = "/{id}")
    public ProductDTO findById(@PathVariable Long id) {
       ProductDTO dto = service.findById(id);
       return dto;
    }

    // Endpoint para buscar todos os produtos
    // Suporta paginação através do Pageable
    // Exemplo: GET /products?page=0&size=10
    @GetMapping
    public Page<ProductDTO> findAll(Pageable pageable) {
        return service.findAll(pageable);
    }

    @PostMapping
    public ProductDTO insert(@RequestBody ProductDTO dto) {
        dto = service.insert(dto);
        return dto;
    }
}
