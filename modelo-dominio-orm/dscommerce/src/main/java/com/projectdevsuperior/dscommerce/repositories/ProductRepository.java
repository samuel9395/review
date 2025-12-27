package com.projectdevsuperior.dscommerce.repositories;

import com.projectdevsuperior.dscommerce.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
