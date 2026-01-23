// Define o pacote onde esta classe está localizada
package com.projectdevsuperior.dscommerce.controllers;

// Importações das classes que serão utilizadas
import com.projectdevsuperior.dscommerce.dto.ProductDTO;
import com.projectdevsuperior.dscommerce.dto.ProductMinDTO;
import com.projectdevsuperior.dscommerce.services.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

/**
 * @RestController Indica que esta classe é um Controller REST.
 * Ela recebe requisições HTTP (GET, POST, PUT, DELETE)
 * e retorna respostas em formato JSON.
 * @RequestMapping("/products") Define a rota base deste controller.
 * Todas as URLs aqui começam com /products
 */
@RestController
@RequestMapping(value = "/products")
public class ProductController {

    /**
     * Injeção de dependência do ProductService.
     * <p>
     * O controller NÃO acessa o banco de dados diretamente.
     * Ele apenas recebe a requisição HTTP e delega a regra
     * de negócio para a camada de serviço.
     */
    @Autowired
    private ProductService service;

    /**
     * BUSCA TODOS OS PRODUTOS
     * Método que responde a requisições GET /products
     * Suporta:
     * - Paginação
     * - Filtro por nome
     * Exemplo de chamada:
     * GET /products?page=0&size=10&name=tv
     */
    @GetMapping
    public ResponseEntity<Page<ProductMinDTO>> findAll(

            /**
             * @RequestParam
             * Recebe parâmetros da URL.
             *
             * name = "name" → nome do parâmetro
             * defaultValue = "" → se não for enviado, assume string vazia
             */
            @RequestParam(name = "name", defaultValue = "") String name,

            /**
             * Pageable
             * Objeto que o Spring monta automaticamente
             * com informações de paginação:
             * page, size, sort
             */
            Pageable pageable
    ) {
        // Chama o service para buscar os produtos
        Page<ProductMinDTO> dto = service.findAll(name, pageable);

        /**
         * ResponseEntity.ok(...)
         * Retorna:
         * - Status HTTP 200 (OK)
         * - Corpo da resposta (Page<ProductDTO>)
         */
        return ResponseEntity.ok(dto);
    }

    /**
     * BUSCAR PRODUTO POR ID
     * GET /products/{id}
     * Exemplo:
     * GET /products/1
     */
    @GetMapping(value = "/{id}")
    public ResponseEntity<ProductDTO> findById(

            /**
             * @PathVariable
             * Captura o valor {id} da URL
             */
            @PathVariable Long id
    ) {
        // Busca o produto pelo id
        ProductDTO dto = service.findById(id);

        // Retorna status 200 com o produto encontrado
        return ResponseEntity.ok(dto);
    }

    /**
     * INSERIR NOVO PRODUTO
     * POST /products
     * Apenas usuários com ROLE_ADMIN podem acessar
     */
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity<ProductDTO> insert(

            /**
             * @RequestBody
             * Indica que os dados vêm no corpo da requisição (JSON).
             *
             * @Valid
             * Ativa as validações definidas no DTO
             * (ex: @NotNull, @NotBlank, etc).
             */
            @Valid @RequestBody ProductDTO dto
    ) {
        // Salva o produto chamando o service
        dto = service.insert(dto);

        /**
         * Cria a URI do novo recurso criado.
         *
         * Exemplo:
         * /products/5
         *
         * Isso é uma boa prática em APIs REST.
         */
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequestUri() // URL atual (/products)
                .path("/{id}")           // adiciona /{id}
                .buildAndExpand(dto.getId()) // substitui {id}
                .toUri();

        /**
         * Retorna:
         * - Status 201 (Created)
         * - Header Location com a URL do novo recurso
         * - Corpo com o ProductDTO criado
         */
        return ResponseEntity.created(uri).body(dto);
    }

    /**
     * ATUALIZAR PRODUTO
     * PUT /products/{id}
     * Apenas ADMIN pode atualizar
     */
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PutMapping(value = "/{id}")
    public ResponseEntity<ProductDTO> update(

            // ID do produto vindo pela URL
            @PathVariable Long id,

            // Dados atualizados vindos no corpo da requisição
            @Valid @RequestBody ProductDTO dto
    ) {
        // Atualiza o produto
        dto = service.update(id, dto);

        // Retorna status 200 com o produto atualizado
        return ResponseEntity.ok(dto);
    }

    /**
     * DELETAR PRODUTO
     * DELETE /products/{id}
     * <p>
     * Apenas ADMIN pode deletar
     */
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(

            // ID do produto a ser removido
            @PathVariable Long id
    ) {
        // Chama o service para deletar
        service.delete(id);

        /**
         * Retorna:
         * - Status 204 (No Content)
         * - Sem corpo na resposta
         */
        return ResponseEntity.noContent().build();
    }
}
