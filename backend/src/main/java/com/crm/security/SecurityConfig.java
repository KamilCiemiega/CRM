package com.crm.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

//

@Configuration
@EnableWebSecurity
public class SecurityConfig {


    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf(csrf ->
                        csrf.disable()
                );
    }



//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http.authorizeRequests(configure -> configure
//                .requestMatchers("/users/save").permitAll()
//                .requestMatchers("users/csrf-token").permitAll()
//                .anyRequest().authenticated()
//        );
//        return http.build();
//    }

//    @Bean
//    SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
//        http
//                // ...
//                .cors(cors -> cors.disable());
//        return http.build();
//    }

//    @Bean
//    public CorsConfigurationSource corsConfigurationSource() {
//        CorsConfiguration configuration = new CorsConfiguration();
//        configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000")); // Ustawia na domenę Twojej aplikacji frontendowej
//        configuration.setAllowedMethods(Arrays.asList("GET","POST")); // Ustawia na metody HTTP, których możesz użyć
//        configuration.setAllowCredentials(true); // Ustawia czy serwer ma zezwalać na przekazywanie ciasteczek w żądaniach
//        configuration.addAllowedHeader("*"); // Ustawia na nagłówki, które są dozwolone w żądaniach
//
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", configuration);
//
//        return source;
//    }

}