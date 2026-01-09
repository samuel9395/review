package com.devsuperior.uri2609.repositories;

import com.devsuperior.uri2609.dto.CategorySumDTO;
import com.devsuperior.uri2609.projections.CategorySumProjection;
import org.springframework.data.jpa.repository.JpaRepository;

import com.devsuperior.uri2609.entities.Category;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query(nativeQuery = true, value =
            "SELECT c.name, SUM(p.amount) AS sum " +
            "FROM categories c " +
            "INNER JOIN products p ON p.id_categories = c.id " +
            "GROUP BY c.name " +
            "ORDER BY c.name"
    )
    List<CategorySumProjection> search1();

    // Nesse caso como o relacionamento de Category era de @OneToMany para produtos(private List<Product> products = new ArrayList<>();),
    // foi usado o Product onde o relacionamento é @ManyToOne para categoria(private Category category)
    @Query(value =
            "SELECT new com.devsuperior.uri2609.dto.CategorySumDTO(obj.category.name, SUM(obj.amount) AS sum) " +
            "FROM Product obj " +
            "GROUP BY obj.category.name " +
            "ORDER BY obj.category.name"
    )
    List<CategorySumDTO> search2();
}
