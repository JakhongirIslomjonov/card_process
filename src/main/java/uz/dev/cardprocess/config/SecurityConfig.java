package uz.dev.cardprocess.config;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import uz.dev.cardprocess.filter.JwtFilter;
import uz.dev.cardprocess.service.jwt.JwtService;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private final CustomUserDetailsService customUserDetailsService;
    private final AuthenticationManager authenticationManager;
    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
    private final ObjectMapper objectMapper;
    private final JwtService jwtService;

    public SecurityConfig(
            @Lazy CustomUserDetailsService customUserDetailsService,
            @Lazy AuthenticationManager authenticationManager,
            @Lazy CustomAuthenticationEntryPoint customAuthenticationEntryPoint,
            @Lazy ObjectMapper objectMapper,
            @Lazy JwtService jwtService) {
        this.customUserDetailsService = customUserDetailsService;
        this.authenticationManager = authenticationManager;
        this.customAuthenticationEntryPoint = customAuthenticationEntryPoint;
        this.objectMapper = objectMapper;
        this.jwtService = jwtService;
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtFilter jwtFilter) throws Exception {
        http.sessionManagement(m -> m.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.exceptionHandling(m -> m.authenticationEntryPoint(customAuthenticationEntryPoint));
        http.csrf(AbstractHttpConfigurer::disable);
        http.authorizeHttpRequests(m -> m
//                .requestMatchers("/api/v1/auth/**", "").permitAll()
                .requestMatchers("/v3/api-docs/**", "/swagger-ui.html", "/api/v1/auth/**", "/swagger-ui/**").permitAll()
                .anyRequest().authenticated());
        http.addFilter(new CustomAuthenticationFilter(authenticationManager, objectMapper, jwtService));
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        daoAuthenticationProvider.setUserDetailsService(customUserDetailsService);
        return daoAuthenticationProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager() {
        return new ProviderManager(authenticationProvider());
    }
}

