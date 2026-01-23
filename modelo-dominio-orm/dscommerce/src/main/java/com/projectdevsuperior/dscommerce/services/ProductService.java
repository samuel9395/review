package com.projectdevsuperior.dscommerce.services;

import com.projectdevsuperior.dscommerce.dto.ProductDTO;
import com.projectdevsuperior.dscommerce.dto.ProductMinDTO;
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

import java.util.Optional;

@Service
public class ProductService {

    // A camada de serviço depende da camada de acesso a dados (Repository)
    @Autowired
    private ProductRepository repository;

    // Método responsável por buscar todos os produtos de forma paginada
    @Transactional(readOnly = true)
    public Page<ProductMinDTO> findAll(String name, Pageable pageable) {

        // Aqui mudamos para o Page, para fazer a busca paginada
        // Adicionada a variável name para também realizar buscas de produto por nome
        Page<Product> result = repository.searchByName(name, pageable);

        // Converte cada Product em ProductDTO e retorna como lista
        return result.map(x -> new ProductMinDTO(x));
    }

    // readOnly = true evita lock desnecessário no banco
    @Transactional(readOnly = true)
    // Método responsável por buscar um produto pelo id
    public ProductDTO findById(Long id) {

        // Busca no banco de dados o id passado como argumento, e retorna para a variável
        // Usado o método <orElseThrow> para lançar uma exceção customizada
        Optional<Product> result = Optional.of(repository
                .findById(id).orElseThrow(
                        () -> new ResourceNotFoundException("Recurso não encontrado!")));

        // Aqui pegamos o objeto Product que está dentro do Optional usando o método get
        Product product = result.get();

        // Aqui copiamos os dados do produto(product) para um novo objeto do tipo ProductDTO
        ProductDTO dto = new ProductDTO(product);

        // Retorna o DTO para o controller
        return dto;
    }

    // Método responsável por inserir novo produto
    @Transactional
    public ProductDTO insert(ProductDTO dto) {

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

        try {
            // Obtém a referência da entidade pelo id
            Product entity = repository.getReferenceById(id);

            // Atualiza os dados da entidade
            copyDtoToEntity(dto, entity);

            // Salva as alterações
            entity = repository.save(entity);

            // Retorna o DTO atualizado
            return new ProductDTO(entity);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Recurso não encontrado: id não existe!");
        }
    }

    // Método responsável por deletar o produto pelo id
    // O parâmetro adicionado na anotação só vai executar a transação se o método estiver no contexto de outra transação.
    // Caso contrário ele não envolve com o Transactional e captura a exceção de integridade do banco de dados
    @Transactional(propagation = Propagation.SUPPORTS)
    public void delete(Long id) {

        // Condicional para validar se o recurso existe antes de deletar,
        // caso não exista é lançada uma exceção customizada
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Recurso não encontrado: id não existe!");
        }

        try {
            // Remove o registro do banco
            repository.deleteById(id);
        } catch (DataIntegrityViolationException e) {

            // Lançamento de exceção customiza para serviço de banco de dados
            throw new DatabaseException("Falha de integridade referencial.");
        }
    }

    // Método auxiliar para copiar dados do DTO para a entidade
    private void copyDtoToEntity(ProductDTO dto, Product entity) {

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
* }
 * */