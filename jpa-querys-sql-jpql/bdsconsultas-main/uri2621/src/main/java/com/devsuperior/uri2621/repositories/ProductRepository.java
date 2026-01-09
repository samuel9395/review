package com.devsuperior.uri2621.repositories;

import com.devsuperior.uri2621.dto.ProductMinDTO;
import com.devsuperior.uri2621.projections.ProductMinProjection;
import org.springframework.data.jpa.repository.JpaRepository;

import com.devsuperior.uri2621.entities.Product;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query(nativeQuery = true, value =
            "SELECT prod.name " +
            "FROM products prod " +
            "INNER JOIN providers prov ON prov.id = prod.id_providers " +
            "WHERE prod.amount BETWEEN 10 AND 20 " +
            "AND prov.name LIKE :product% ")
    List<ProductMinProjection> search1(String product);

    @Query(value =
            "SELECT new com.devsuperior.uri2621.dto.ProductMinDTO(prod.name) " +
                    "FROM Product prod " +
                    "WHERE prod.amount BETWEEN 10 AND 20 " +
                    "AND prod.provider.name LIKE :product% ")
    List<ProductMinDTO> search2(String product);
}
