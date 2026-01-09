package com.devsuperior.uri2737.repositories;

import com.devsuperior.uri2737.projections.LawyerMinProjection;
import org.springframework.data.jpa.repository.JpaRepository;

import com.devsuperior.uri2737.entities.Lawyer;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LawyerRepository extends JpaRepository<Lawyer, Long> {

    // Por ser uma consulta em SQL raiz, o customers_number estava retornando null.
    // Por isso foi inserido "AS customersNumber" para casar com o argumento da LawyerMinProjection
    @Query(nativeQuery = true, value =
            "SELECT name, customers_number AS customersNumber " +
            "FROM lawyers " +
            "WHERE customers_number IN ( " +
            "(SELECT MAX(customers_number) " +
            "FROM lawyers), " +
            "(SELECT MIN(customers_number) " +
            " FROM lawyers) " +
            ") " +
            "UNION  " +
            "SELECT 'Average', ROUND(AVG(customers_number),0) " +
            "FROM lawyers")
    List<LawyerMinProjection> search1();

}
