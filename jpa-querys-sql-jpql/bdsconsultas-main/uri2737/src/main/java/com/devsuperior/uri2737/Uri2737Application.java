package com.devsuperior.uri2737;

import com.devsuperior.uri2737.dto.LawyerMinDTO;
import com.devsuperior.uri2737.projections.LawyerMinProjection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.devsuperior.uri2737.repositories.LawyerRepository;

import java.util.List;
import java.util.stream.Collectors;

@SpringBootApplication
public class Uri2737Application implements CommandLineRunner {

	@Autowired
	private LawyerRepository repository;
	
	public static void main(String[] args) {
		SpringApplication.run(Uri2737Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

        List<LawyerMinProjection> list = repository.search1();
        List<LawyerMinDTO> result1 = list.stream().map(x -> new LawyerMinDTO(x)).collect(Collectors.toList());

        System.out.println("\n====SQL QUERY====");
        for(LawyerMinDTO dto : result1) {
            System.out.println(dto);
        }
        System.out.println("=================\n");

        System.out.println("\n====JPQL QUERY====");
        System.out.println("A JPQL não tem união ao fazer a query...");
        System.out.println("==================");
	}
}
