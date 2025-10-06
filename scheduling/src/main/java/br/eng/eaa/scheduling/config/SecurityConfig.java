package br.eng.eaa.scheduling.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Para simplicidade, desabilitado
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/graphql").authenticated() // GraphQL requer auth
                        .requestMatchers("/appointments/**").authenticated() // Endpoints REST
                        .anyRequest().permitAll()
                )
                .httpBasic(); // Autenticação básica
        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails medico = User.withDefaultPasswordEncoder()
                .username("medico")
                .password("password")
                .roles("MEDICO")
                .build();

        UserDetails enfermeiro = User.withDefaultPasswordEncoder()
                .username("enfermeiro")
                .password("password")
                .roles("ENFERMEIRO")
                .build();

        UserDetails paciente = User.withDefaultPasswordEncoder()
                .username("paciente")
                .password("password")
                .roles("PACIENTE")
                .build();

        return new InMemoryUserDetailsManager(medico, enfermeiro, paciente);
    }
}