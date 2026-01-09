package com.devsuperior.uri2621;

import com.devsuperior.uri2621.dto.ProductMinDTO;
import com.devsuperior.uri2621.projections.ProductMinProjection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.devsuperior.uri2621.repositories.ProductRepository;

import java.util.List;
import java.util.stream.Collectors;

@SpringBootApplication
public class Uri2621Application implements CommandLineRunner {

	@Autowired
	private ProductRepository repository;
	
	public static void main(String[] args) {
		SpringApplication.run(Uri2621Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

        System.out.println("\nConsultando nome dos produtos cujas quantidades estão entre 10 e 20" +
                " e cujo nome do fornecedor inicie com a letra ‘P’.\n");

        List<ProductMinProjection> list = repository.search1("P");
        List<ProductMinDTO> result1 = list.stream().map(x -> new ProductMinDTO(x)).collect(Collectors.toList());

        System.out.println("\n====SQL QUERY====");
        for (ProductMinDTO obj : result1) {
            System.out.println(obj);
        }
        System.out.println("=================\n");

        List<ProductMinDTO> result2 = repository.search2("P");

        System.out.println("\n====JPQL QUERY====");
        for (ProductMinDTO obj : result2) {
            System.out.println(obj);
        }
        System.out.println("==================");


    }
}
