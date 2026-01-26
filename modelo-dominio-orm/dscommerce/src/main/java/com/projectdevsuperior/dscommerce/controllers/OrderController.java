package com.projectdevsuperior.dscommerce.controllers;

import com.projectdevsuperior.dscommerce.dto.OrderDTO;
import com.projectdevsuperior.dscommerce.dto.ProductDTO;
import com.projectdevsuperior.dscommerce.services.OrderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

// Controller responsável por expor os endpoints relacionados a pedidos
@RestController
@RequestMapping(value = "/orders")
public class OrderController {

    @Autowired
    private OrderService service;

    // Endpoint protegido: apenas usuários com perfil ADMIN podem acessar
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping(value = "/{id}")
    public ResponseEntity<OrderDTO> findById(@PathVariable Long id) {
        OrderDTO dto = service.findById(id);
        return ResponseEntity.ok(dto);
    }

    // Garante que apenas usuários com perfil CLIENT possam criar pedidos
    @PreAuthorize("hasAnyRole('ROLE_CLIENT')")
    @PostMapping
    public ResponseEntity<OrderDTO> insert(@RequestBody OrderDTO dto) {

        // Delegação da regra de negócio para a camada de serviço
        dto = service.insert(dto);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path("/{id}")
                .buildAndExpand(dto.getId())
                .toUri();
        // Retorna HTTP 201 (Created) com o corpo e o header Location
        return ResponseEntity.created(uri).body(dto);
    }

}
