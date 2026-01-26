package com.projectdevsuperior.dscommerce.dto;

import com.projectdevsuperior.dscommerce.entities.OrderItem;

// DTO que representa um ‘item’ do pedido
public class OrderItemDTO {

    private Long productId;
    private String name;
    private Double price;
    private Integer quantity;

    public OrderItemDTO(Long productId, String name, Double price, Integer quantity) {
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    // Extrai informações do produto a partir da entidade OrderItem
    public OrderItemDTO(OrderItem enetity) {
        productId = enetity.getProduct().getId();
        name = enetity.getProduct().getName();
        price = enetity.getPrice();
        quantity = enetity.getQuantity();
    }

    public Long getProductId() {
        return productId;
    }

    public String getName() {
        return name;
    }

    public Double getPrice() {
        return price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    // Calcula o subtotal do ‘item’ (preço x quantidade)
    public double getSubTotal() {
        return price * quantity;
    }
}
