package com.projectdevsuperior.dscommerce.repositories;

import com.projectdevsuperior.dscommerce.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {}
