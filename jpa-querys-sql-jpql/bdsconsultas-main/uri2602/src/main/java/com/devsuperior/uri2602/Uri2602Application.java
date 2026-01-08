package com.devsuperior.uri2602;

import com.devsuperior.uri2602.dto.CustomerMinDTO;
import com.devsuperior.uri2602.projections.CustomerMinProjection;
import com.devsuperior.uri2602.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;
import java.util.stream.Collectors;

@SpringBootApplication
public class Uri2602Application implements CommandLineRunner {

    @Autowired
    private CustomerRepository repository;

	public static void main(String[] args) {
		SpringApplication.run(Uri2602Application.class, args);
	}

    @Override
    public void run(String... args) throws Exception {

        System.out.println("\nRunning query Uri2602Application...\n");

        // Aqui passamos com argumento o 'RS' que seria o valor da consulta em SQL
        List<CustomerMinProjection> list = repository.search1("rs");
        List<CustomerMinDTO> result1 = list.stream().map(x -> new CustomerMinDTO(x)).collect(Collectors.toList());

        // RESULTADO SQL
        // Pra cada objeto dentro da lista, imprimos o obj.getName
        for (CustomerMinDTO obj : result1) {
            System.out.println(obj.getName());
        }
        System.out.println("\n\n");

        List<CustomerMinDTO> result2 =  repository.search2("rs");

        // RESULTADO JPQL
        for (CustomerMinDTO obj : result2) {
            System.out.println(obj);
        }
    }
}
