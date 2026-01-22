package com.projectdevsuperior.dscommerce.dto;

import com.projectdevsuperior.dscommerce.entities.User;
import org.springframework.security.core.GrantedAuthority;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class UserDTO {

    // Dados básicos do usuário
    private Long id;
    private String name;
    private String email;
    private String phone;
    private LocalDate brithDate;

    // Lista de nomes das roles (ex: ROLE_ADMIN)
    private List<String> roles = new ArrayList<>();

    /**
     * Construtor que converte a entidade User em DTO.
     * @param entity
     */
    public UserDTO(User entity) {
        id = entity.getId();
        name = entity.getName();
        email = entity.getEmail();
        phone = entity.getPhone();
        brithDate = entity.getBrithDate();

        // Converte GrantedAuthority para String
        for (GrantedAuthority role : entity.getRoles()) {
            roles.add(role.getAuthority());
        }
    }

    // Getters
    public Long getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }
    public LocalDate getBrithDate() { return brithDate; }
    public List<String> getRoles() { return roles; }
}
