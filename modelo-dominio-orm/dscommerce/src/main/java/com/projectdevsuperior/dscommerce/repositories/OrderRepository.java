package com.projectdevsuperior.dscommerce.repositories;

import com.projectdevsuperior.dscommerce.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;

// Repositório JPA responsável pelo acesso aos dados de pedidos
public interface OrderRepository extends JpaRepository<Order, Long> {

}
