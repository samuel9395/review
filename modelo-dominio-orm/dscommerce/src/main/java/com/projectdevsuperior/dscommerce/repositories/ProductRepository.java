package com.projectdevsuperior.dscommerce.repositories;

import com.projectdevsuperior.dscommerce.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProductRepository extends JpaRepository<Product, Long> {

    // Consulta JPQL customizada para usar no findAll
    // Nessa query utilizando o LIKE e o argumento entre '%'
    @Query(value =
            "SELECT obj " +
            "FROM Product obj " +
            "WHERE UPPER(obj.name) " +
            "LIKE UPPER(CONCAT('%', :name, '%'))")
    Page<Product> searchByName(String name, Pageable pageable);
}
