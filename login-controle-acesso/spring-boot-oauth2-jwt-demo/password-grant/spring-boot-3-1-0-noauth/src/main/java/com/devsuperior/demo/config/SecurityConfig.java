package com.devsuperior.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable()); // Aqui acessa o objeto http do tipo HttpSecurity e chamando o csrf.disable, para desabilitar a proteção contra ataques do tipo csrf(dados da seção)
        http.authorizeHttpRequests(auth -> auth.anyRequest().permitAll()); // Aqui chama outra configuração para configurar a permissão para os endpoints da aplicação
        return http.build();
    }

}
