package com.projectdevsuperior.dscommerce.repositories;

import com.projectdevsuperior.dscommerce.entities.OrderItem;
import com.projectdevsuperior.dscommerce.entities.OrderItemPK;
import org.springframework.data.jpa.repository.JpaRepository;

// Repositório responsável pela persistência dos itens do pedido
// Utiliza chave composta (OrderItemPK)
public interface OrderItemRepository extends JpaRepository<OrderItem, OrderItemPK> {

}
