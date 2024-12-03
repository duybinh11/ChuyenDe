package com.example.demo.Config;

import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;

import com.example.demo.Filter.JwtCookieFilter;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    private String[] endPointPost = { "/login" };
    private String[] endPointGet = { "/login", "/register", "/index", "detail_item", "/category_item" };

    @Autowired
    private CustomJwtDecoder customJwtDecoder;

    @Autowired
    private JwtCookieFilter jwtCookieFilter;

    @Bean
    SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        System.out.println("sds");

        // Đăng ký filter tùy chỉnh để trích xuất token từ cookie và thêm vào header
        httpSecurity.addFilterBefore(jwtCookieFilter,
                org.springframework.security.oauth2.server.resource.web.BearerTokenAuthenticationFilter.class);

        httpSecurity.authorizeHttpRequests(
                request -> request.requestMatchers(HttpMethod.POST, endPointPost).permitAll()
                        .requestMatchers("/public/**").permitAll()
                        .requestMatchers(HttpMethod.GET, endPointGet).permitAll()
                        .requestMatchers("/css/**", "/js/**", "/Img/**").permitAll()
                        .anyRequest().authenticated()

        );

        httpSecurity.oauth2ResourceServer(
                oauth2 -> oauth2.jwt(jwtConfig -> jwtConfig.decoder(customJwtDecoder)));

        httpSecurity.csrf(t -> t.disable());
        return httpSecurity.build();
    }

    @Bean
    JwtDecoder jwtDecoder() {
        String sign = "6dk5jxch5pwcqdw3bagw5z7pd7g7zxu40n7ndeb8tpz8hld0j5dtkdzb6xv8jxb3";
        SecretKeySpec secretKeySpec = new SecretKeySpec(sign.getBytes(), "HS512");
        return NimbusJwtDecoder.withSecretKey(secretKeySpec).macAlgorithm(MacAlgorithm.HS512).build();
    }
}
