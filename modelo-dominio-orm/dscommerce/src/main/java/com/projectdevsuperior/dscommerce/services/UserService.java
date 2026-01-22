package com.projectdevsuperior.dscommerce.services;

import com.projectdevsuperior.dscommerce.dto.UserDTO;
import com.projectdevsuperior.dscommerce.entities.Role;
import com.projectdevsuperior.dscommerce.entities.User;
import com.projectdevsuperior.dscommerce.projections.UserDetailsProjection;
import com.projectdevsuperior.dscommerce.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class UserService implements UserDetailsService {

    // Repositório responsável pelo acesso aos dados do usuário
    @Autowired
    private UserRepository repository;

    /**
     * Método exigido pelo Spring Security.
     * Carrega o usuário e seus perfis (roles) a partir do email.
     * @param username the username identifying the user whose data is required.
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // Busca o usuário e suas roles em uma única consulta
        List<UserDetailsProjection> result = repository.searchUserAndRolesByEmail(username);
        if (result.size() == 0) {
            throw new UsernameNotFoundException("Email not found");
        }

        // Monta manualmente o objeto User a partir da projeção
        User user = new User();
        user.setEmail(result.get(0).getUsername());
        user.setPassword(result.get(0).getPassword());

        // Associa todas as roles retornadas ao usuário
        for (UserDetailsProjection projection : result) {
            user.addRole(new Role(projection.getRoleId(), projection.getAuthority()));
        }

        return user;
    }

    /**
     * Recupera o usuário autenticado a partir do token JWT.
     * @return
     */
    protected User authenticated() {

        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            Jwt jwtPrincipal = (Jwt) authentication.getPrincipal();
            String username = jwtPrincipal.getClaim("username");

            return repository.findByEmail(username).get();
        } catch (Exception e) {
            throw new UsernameNotFoundException("Email not found");
        }
    }

    /**
     * Retorna os dados do usuário autenticado.
     * @return
     */
    @Transactional(readOnly = true)
    public UserDTO getMe() {
        User user = authenticated();
        return new UserDTO(user);
    }
}
