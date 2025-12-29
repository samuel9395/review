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
    public ProductDTO insert(ProductDTO dto) {                 // Método responsável por inserir novo produto

        // Cria uma nova entidade Product inserindo os campos á seguir
        Product entity = new Product();

        // Aqui posso usar também o método copyDtoToENtity, como feito no método update
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setPrice(dto.getPrice());
        entity.setImgUrl(dto.getImgUrl());

        // Salva no banco de dados
        entity = repository.save(entity);

        // Retorna o DTO
        return new ProductDTO(entity);
    }

    // Método responsável por atualizar um produto existente
    @Transactional
    public ProductDTO update(Long id, ProductDTO dto) {

        // Obtém a referência da entidade pelo id
        Product entity = repository.getReferenceById(id);

        // Atualiza os dados da entidade
        copyDtoToENtity(dto, entity);

        // Salva as alterações
        entity = repository.save(entity);

        // Retorna o DTO atualizado
        return new ProductDTO(entity);
    }

    // Método auxiliar para copiar dados do DTO para a entidade
    private void copyDtoToENtity(ProductDTO dto, Product entity) {

        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setPrice(dto.getPrice());
        entity.setImgUrl(dto.getImgUrl());
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