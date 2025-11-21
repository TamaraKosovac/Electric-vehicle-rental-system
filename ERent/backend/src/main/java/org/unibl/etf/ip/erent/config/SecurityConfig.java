package org.unibl.etf.ip.erent.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.unibl.etf.ip.erent.model.Role;
import org.unibl.etf.ip.erent.security.JwtAuthenticationFilter;
import java.util.List;

@Configuration
public class SecurityConfig implements WebMvcConfigurer {

    private final JwtAuthenticationFilter jwtFilter;

    public SecurityConfig(JwtAuthenticationFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .cors(cors -> {})
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/login").permitAll()
                        .requestMatchers("/api/rss").permitAll()
                        .requestMatchers("/images/**").permitAll()
                        .requestMatchers("/api/employees/**")
                        .hasAnyRole(Role.ADMIN.name(), Role.MANAGER.name())
                        .requestMatchers("/api/vehicles/**").hasAnyRole(Role.ADMIN.name(), Role.MANAGER.name(), Role.OPERATOR.name())
                        .requestMatchers("/api/cars/**").hasAnyRole(Role.ADMIN.name(), Role.MANAGER.name())
                        .requestMatchers("/api/scooters/**").hasAnyRole(Role.ADMIN.name(), Role.MANAGER.name())
                        .requestMatchers("/api/bikes/**").hasAnyRole(Role.ADMIN.name(), Role.MANAGER.name())
                        .requestMatchers("/api/manufacturers/**").hasAnyRole(Role.ADMIN.name(), Role.MANAGER.name())
                        .requestMatchers("/api/clients/**").hasAnyRole(Role.ADMIN.name(), Role.MANAGER.name(), Role.OPERATOR.name())
                        .requestMatchers("/api/rentals/**").hasAnyRole(Role.OPERATOR.name(), Role.MANAGER.name())
                        .requestMatchers("/api/malfunctions/**").hasAnyRole(Role.OPERATOR.name(), Role.MANAGER.name())
                        .requestMatchers("/api/rental-prices/**").hasRole(Role.MANAGER.name())
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:4200"));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Value("${file.upload-dir}")
    private String uploadDir;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/images/**")
                .addResourceLocations("file:" + uploadDir + "/");
    }
}