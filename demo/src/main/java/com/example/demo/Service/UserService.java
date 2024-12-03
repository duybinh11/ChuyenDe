package com.example.demo.Service;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.Entity.CartEntity;
import com.example.demo.Entity.ItemEntity;
import com.example.demo.Entity.UserEntity;
import com.example.demo.Repository.ItemRepository;
import com.example.demo.Repository.UserRepository;
import com.example.demo.Request.RequestCreateUser;
import com.example.demo.Request.RequestLogin;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSObject;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.Payload;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private ItemRepository itemRepository;

    static final String SIGNER_KEY = "6dk5jxch5pwcqdw3bagw5z7pd7g7zxu40n7ndeb8tpz8hld0j5dtkdzb6xv8jxb3";

    public UserEntity createUser(RequestCreateUser request) {
        try {
            UserEntity user = mapper.map(request, UserEntity.class);
            PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            return userRepository.save(user);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public UserEntity handleLogin(RequestLogin requestLogin, HttpServletResponse response) {
        UserEntity user = userRepository.findByUsername(requestLogin.getUsername());

        if (user == null) {
            throw new RuntimeException("User not found");
        }

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        if (passwordEncoder.matches(requestLogin.getPassword(), user.getPassword())) {
            String token = generateToken(user);
            System.out.println(token);

            Cookie cookie = new Cookie("auth_token", token);
            cookie.setHttpOnly(true);
            cookie.setSecure(true);
            cookie.setPath("/");
            cookie.setMaxAge(3600);
            response.addCookie(cookie);

            return user;
        } else {
            throw new RuntimeException("Invalid password");
        }

    }

    @Transactional()
    public void addCart(String usernaem, int idItem, int count) {

        UserEntity userEntity = userRepository.findByUsername(usernaem);
        ItemEntity itemEntity = itemRepository.findById(idItem).get();
        userEntity.addCart(itemEntity, 3);

        userRepository.save(userEntity);
    }

    @Transactional
    public List<CartEntity> getCartByUsername(String username) {
        UserEntity userEntity = userRepository.findByUsername(username);
        return userEntity.getCarts();
    }

    private String generateToken(UserEntity user) {
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);

        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(user.getUsername())
                .issueTime(new Date())
                .expirationTime(new Date(
                        Instant.now().plus(1, ChronoUnit.HOURS).toEpochMilli()))
                .jwtID(UUID.randomUUID().toString())
                .build();

        Payload payload = new Payload(jwtClaimsSet.toJSONObject());

        JWSObject jwsObject = new JWSObject(header, payload);
        try {
            jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes()));
            return jwsObject.serialize();
        } catch (Exception e) {
            throw new RuntimeException("sfd");
        }

    }

    public SignedJWT verifyToken(String token, boolean isfresh) throws ParseException, JOSEException {
        SignedJWT signedJWT = SignedJWT.parse(token);
        JWSVerifier jwsVerifier = new MACVerifier(SIGNER_KEY.getBytes());

        var resultVerify = signedJWT.verify(jwsVerifier);
        Date expiryTime = signedJWT.getJWTClaimsSet().getExpirationTime();
        if (!(resultVerify && expiryTime.after(new Date()))) {
            throw new RuntimeException("xac thuc token logout loi");
        }

        return signedJWT;
    }

}
