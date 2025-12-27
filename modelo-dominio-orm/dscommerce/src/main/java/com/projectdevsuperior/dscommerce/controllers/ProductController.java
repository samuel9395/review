package com.projectdevsuperior.dscommerce.controllers;

import com.projectdevsuperior.dscommerce.dto.ProductDTO;
import com.projectdevsuperior.dscommerce.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/products")
public class ProductController {

    // O controller depende do service

    @Autowired
    private ProductService service;

    @GetMapping(value = "/{id}")                            // Rota id dentro da rota products
    public ProductDTO findById(@PathVariable Long id) {
       ProductDTO dto = service.findById(id);
       return dto;
    }
}
