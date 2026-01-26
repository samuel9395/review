package com.projectdevsuperior.dscommerce.services;

import com.projectdevsuperior.dscommerce.dto.OrderDTO;
import com.projectdevsuperior.dscommerce.entities.Order;
import com.projectdevsuperior.dscommerce.repositories.OrderRepository;
import com.projectdevsuperior.dscommerce.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

// Camada de serviço responsável pela regra de negócio do pedido
@Service
public class OrderService {

    @Autowired
    private OrderRepository repository;

    // Transação somente leitura para melhorar desempenho
    @Transactional(readOnly = true)
    public OrderDTO findById(Long id) {

        Order order = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Recurso não encontrado"));

        return new OrderDTO(order);
    }

}
