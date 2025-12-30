package com.projectdevsuperior.dscommerce.controllers;

import com.projectdevsuperior.dscommerce.dto.ProductDTO;
import com.projectdevsuperior.dscommerce.services.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(value = "/products")
public class ProductController {

    // O controller não acessa o banco de dados diretamente
    // Ele delega a lógica para a camada de serviço
    @Autowired
    private ProductService service;

    // Introduzido a classe ResponseEntity<> para melhorar a resposta
    // com o status http(200, 201, 404), o corpo da resposta(DTO, lista, página) e os headers(Location, Authorization).

    // Endpoint para buscar todos os produtos
    // Suporta paginação através do Pageable
    // Exemplo: GET /products?page=0&size=10
    @GetMapping
    public ResponseEntity<Page<ProductDTO>> findAll(Pageable pageable) {
        Page<ProductDTO> dto = service.findAll(pageable);
        return ResponseEntity.ok(dto);
    }

    // Endpoint para buscar um produto pelo id
    // Exemplo: GET /products/1
    @GetMapping(value = "/{id}")
    public ResponseEntity<ProductDTO> findById(@PathVariable Long id) {
       ProductDTO dto = service.findById(id);
       return ResponseEntity.ok(dto);
    }

    // Endpoint para inserir novos produtos
    // POST /products
    @PostMapping
    public ResponseEntity<ProductDTO> insert(@Valid @RequestBody ProductDTO dto) {

        // Chama o service para salvar o produto
        dto = service.insert(dto);

        // Monta a URI do novo recurso criado
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}")
                .buildAndExpand(dto.getId()).toUri();

        // Retorna o status 201 Created com o corpo do DTO
        return ResponseEntity.created(uri).body(dto);
    }

    // Endpoint para atualizar um produto pelo id
    // PUT /products/{id}
    @PutMapping(value = "/{id}")
    public ResponseEntity<ProductDTO> update(@PathVariable Long id, @Valid @RequestBody ProductDTO dto) {
        dto = service.update(id, dto);
        return ResponseEntity.ok(dto);
    }

    // Endpoint por deletar um produto pelo id
    // DELETE /products/{id}
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
