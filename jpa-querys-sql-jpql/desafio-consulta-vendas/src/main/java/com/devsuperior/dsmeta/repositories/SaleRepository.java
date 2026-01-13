package com.devsuperior.dsmeta.repositories;

import com.devsuperior.dsmeta.dto.SaleMinDTO;
import com.devsuperior.dsmeta.entities.Sale;
import com.devsuperior.dsmeta.entities.Seller;
import com.devsuperior.dsmeta.projections.SaleProjection;
import com.devsuperior.dsmeta.projections.SellerProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

// Repositório responsável por acessar os dados da entidade Sale no banco
public interface SaleRepository extends JpaRepository<Sale, Long> {

    /*
     * Consulta nativa (SQL puro) para gerar o relatório de vendas.
     *
     * - Busca: id da venda, data, valor e nome do vendedor
     * - Faz JOIN entre as tabelas tb_sales e tb_seller
     * - Filtra pelo nome do vendedor (LIKE)
     * - Filtra pelo período de datas
     * - Ordena pelo id da venda
     *
     * O retorno é uma Projection, não uma entidade.
     */
    @Query(nativeQuery = true, value =
            "SELECT tb_sales.id, tb_sales.date, tb_sales.amount, tb_seller.name " +
            "FROM tb_sales " +
            "LEFT JOIN tb_seller " +
            "ON tb_seller.id = tb_sales.seller_id " +
            "WHERE UPPER(tb_seller.name) LIKE UPPER(CONCAT('%', :name, '%')) AND tb_sales.date BETWEEN :minDate AND :maxDate " +
            "ORDER BY tb_sales.id ")
    List<SaleProjection> getReport(String name, LocalDate minDate, LocalDate maxDate);

    /*
     * Consulta nativa para gerar o SUMÁRIO DE VENDAS POR VENDEDOR.
     *
     * O que essa query faz:
     * - Busca o nome do vendedor
     * - Soma o valor total das vendas (SUM)
     * - Filtra pelo período de datas
     * - Agrupa os resultados por vendedor
     * - Ordena pelo nome do vendedor
     *
     * Retorna uma Projection (SellerProjection).
     */
    @Query(nativeQuery = true, value =
            "SELECT tb_seller.name, SUM(tb_sales.amount) AS total " +
            "FROM tb_seller " +
            "LEFT JOIN tb_sales " +
            "ON tb_sales.seller_id = tb_seller.id " +
            "WHERE tb_sales.date BETWEEN :minDate AND :maxDate " +
            "GROUP BY tb_seller.name " +
            "ORDER BY tb_seller.name ")
    List<SellerProjection> getSummary(LocalDate minDate, LocalDate maxDate);
}
