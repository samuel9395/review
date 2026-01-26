package com.projectdevsuperior.dscommerce.services;

import com.projectdevsuperior.dscommerce.dto.OrderDTO;
import com.projectdevsuperior.dscommerce.dto.OrderItemDTO;
import com.projectdevsuperior.dscommerce.entities.*;
import com.projectdevsuperior.dscommerce.repositories.OrderItemRepository;
import com.projectdevsuperior.dscommerce.repositories.OrderRepository;
import com.projectdevsuperior.dscommerce.repositories.ProductRepository;
import com.projectdevsuperior.dscommerce.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

// Camada de serviço responsável pela regra de negócio do pedido
@Service
public class OrderService {

    @Autowired
    private OrderRepository repository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private UserService userService;

    // Transação somente leitura para melhorar desempenho
    @Transactional(readOnly = true)
    public OrderDTO findById(Long id) {

        Order order = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Recurso não encontrado"));

        return new OrderDTO(order);
    }

    @Transactional
    public OrderDTO insert(OrderDTO dto) {

        // Criação da entidade Order
        Order order = new Order();

        // Define o momento da criação do pedido
        order.setMoment(Instant.now());

        // Define o status inicial do pedido
        order.setStatus(OrderStatus.WAITING_PAYMENT);

        // Obtém o usuário autenticado e associa como cliente do pedido
        User user = userService.authenticated();
        order.setClient(user);

        // Converte os itens do DTO em entidades OrderItem
        for (OrderItemDTO itemDTO : dto.getItems()) {

            // Obtém uma referência do produto sem acesso imediato ao banco
            Product product = productRepository.getReferenceById(itemDTO.getProductId());

            // Cria o ‘item’ do pedido com preço capturado no momento da compra
            OrderItem orderItem = new OrderItem(order, product, itemDTO.getQuantity(), product.getPrice());
            order.getItems().add(orderItem);
        }

        // Persiste o pedido
        repository.save(order);

        // Persiste todos os itens associados ao pedido
        orderItemRepository.saveAll(order.getItems());

        // Retorna o pedido convertido para DTO
        return new OrderDTO(order);
    }
}
