package com.example.demo.Config;

import java.text.ParseException;
import java.util.Objects;

import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.stereotype.Component;

import com.example.demo.Service.UserService;

@Component
public class CustomJwtDecoder implements JwtDecoder {

    @Autowired
    private UserService userService;

    private NimbusJwtDecoder nimbusJwtDecoder = null;
    private String KEY = "6dk5jxch5pwcqdw3bagw5z7pd7g7zxu40n7ndeb8tpz8hld0j5dtkdzb6xv8jxb3";

    @Override
    public Jwt decode(String token) throws JwtException {
        try {
            userService.verifyToken(token, false);
        } catch (Exception e) {
            throw new RuntimeException("xac thuc token trong securey " + e.getMessage());
        }

        if (Objects.isNull(nimbusJwtDecoder)) {
            SecretKeySpec secretKey = new SecretKeySpec(KEY.getBytes(), "HS512");
            nimbusJwtDecoder = NimbusJwtDecoder.withSecretKey(secretKey).macAlgorithm(MacAlgorithm.HS512).build();
        }
        return nimbusJwtDecoder.decode(token);
    }

}
