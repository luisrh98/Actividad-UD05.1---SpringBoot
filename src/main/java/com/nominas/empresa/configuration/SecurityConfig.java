package com.nominas.empresa.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import com.nominas.empresa.services.UserService;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public UserDetailsService userDetailsService(UserService userService) {
        return userService; // Usa UserService como UserDetailsService
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(UserService userService) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userService);
        provider.setPasswordEncoder(passwordEncoder());
        
        System.out.println("🔐 AuthenticationProvider configurado correctamente con BCrypt.");
        
        return provider;
    }


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // Desactiva CSRF si no es necesario
            .authorizeHttpRequests(auth -> auth
                    .requestMatchers("/auth/**").permitAll() 
                    .requestMatchers("/**").authenticated()
            )
            .formLogin(form -> form
                    .loginPage("/auth/login") // URL del login personalizado
                    .defaultSuccessUrl("/", true) // Redirigir después de login exitoso
                    .failureUrl("/auth/login?error=true")
                    .permitAll()
            )
            .logout(logout -> logout
                    .logoutUrl("/empresa/logout")  // URL para cerrar sesión
                    .logoutSuccessUrl("/auth/login") // Redirigir tras logout
                    .permitAll()
            );
        
        return http.build();
    }
}