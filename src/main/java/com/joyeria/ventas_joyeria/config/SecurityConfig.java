/*/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
*/
package com.joyeria.ventas_joyeria.config;

import com.joyeria.ventas_joyeria.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
public class SecurityConfig {
    private final UserService userService;


    public SecurityConfig(UserService userService) {
        this.userService = userService;
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                //.formLogin(Customizer.withDefaults())
                .formLogin(form -> form
                .loginPage("/login").permitAll())
                .logout(logout -> logout
                .logoutUrl("/logout-success") // URL para el cierre de sesión
                .logoutSuccessUrl("/login") // Redirección tras el cierre de sesión
                .invalidateHttpSession(true) // Invalidar la sesión
                .deleteCookies("JSESSIONID") // Eliminar cookies de sesión
                .permitAll()) // Permitir el acceso al cierre de sesión sin autenticación
                .authorizeHttpRequests(req -> req //securityFilterChain
                //.requestMatchers("/login/**").permitAll() //Permite el acceso sin autenticación - página de inicio de sesión sin estar autenticados
                .requestMatchers("/static/**", "/images/**").permitAll()
                .requestMatchers("/upload/**").permitAll() // Permitir la carga de archivos sin autenticación

                .requestMatchers("/admin/**").hasAnyAuthority("admin")//solo los usuarios con el rol admin pueden acceder a rutas de administración.
                .requestMatchers("/soporte/**").hasAnyAuthority("admin", "soporte")//los usuarios con rol admin o staff pueden acceder a las rutas de personal.
                .anyRequest().authenticated())//cualquier acceso que no esté explícitamente permitido será bloqueado
               .exceptionHandling(exception -> exception
                .accessDeniedPage("/error/403"))
                
                .userDetailsService(userService).build();//crea una instancia de securityFilterChain
        
    }

    @Bean
    PasswordEncoder passwordEncoder() { //algoritmo de encriptación BCrypt
        return new BCryptPasswordEncoder();
    }
} 
