package com.ashik.WhatsApp.Config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;


@NoArgsConstructor
@Service
public class JwTokenProvider {



    SecretKey key = Keys.hmacShaKeyFor(JwtConstant.SECRET_KEY.getBytes(StandardCharsets.UTF_8));



    public  String generateToken(Authentication authentication){

        String jwt = Jwts.builder().setIssuer("code with ashik")
                .setIssuedAt(new Date()).setExpiration(new Date(new Date().getTime()+86400000))
                .claim("email",authentication.getName())
                .signWith(key)
                .compact();

        return jwt;

    }

    public  String getEmailFromToken(String jwt){

        jwt = jwt.substring(7);
        Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwt).getBody();
        String email = String.valueOf(claims.get("email"));

        return  email;

    }


}
