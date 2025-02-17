package com.example.masterplanbbe.security.config;

import com.example.masterplanbbe.security.exception.JwtAccessDenyHandler;
import com.example.masterplanbbe.security.exception.JwtAuthenticationEntryPoint;
import com.example.masterplanbbe.security.filter.CustomLoginFilter;
import com.example.masterplanbbe.security.filter.JwtAuthenticationFilter;
import com.example.masterplanbbe.security.filter.JwtAuthorizationFilter;
import com.example.masterplanbbe.security.handler.CustomLogoutHandler;
import com.example.masterplanbbe.security.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig {
    // client url
    private String clientUrl;

    // Dependency Injection
    private final UserDetailsService userDetailsService;
    private final JwtService jwtService;
    private final AuthenticationConfiguration authenticationConfiguration;
    private final CustomLogoutHandler customLogoutHandler;
    private final JwtAccessDenyHandler jwtAccessDenyHandler;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    // Crypt Configuration
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Authentication Manager
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public CustomLoginFilter customLoginFilter() throws Exception {
        CustomLoginFilter filter = new CustomLoginFilter(jwtService);
        filter.setAuthenticationManager(authenticationManager(authenticationConfiguration));
        return filter;
    }

    // Security Filter Chain Configuration
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable);
        http.cors(cors -> cors.configurationSource(corsConfigurationSource()));
        http.sessionManagement(sessionManagement ->
                sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        );

        http.exceptionHandling(e -> e
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .accessDeniedHandler(jwtAccessDenyHandler));

        http.formLogin(AbstractHttpConfigurer::disable);
        http.logout(l -> l
                .logoutUrl("/api/v1/member/logout")
                .addLogoutHandler(customLogoutHandler));

        http.authorizeHttpRequests(a -> a
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/v1/member/create").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/v1/member/login").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/v1/member/test").permitAll()
                .anyRequest().authenticated()
        );

        http.addFilterBefore(new JwtAuthorizationFilter(jwtService), CustomLoginFilter.class);
        http.addFilterBefore(new JwtAuthenticationFilter(jwtService, userDetailsService), CustomLoginFilter.class);
        http.addFilterBefore(customLoginFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    // CORS Configuration
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Collections.singletonList(clientUrl));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
