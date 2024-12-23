package com.example.foodpanda_microservices_warehouse.utility;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    private String secretKey = "ajhdhfdkhsjfhdjshfsjdfhsjdhfsjkdhfsjdhfksjdhfkjshdkjfhksjhdfkjhskjdhfkjsh";
    private long expiry = 8640000;


    private Key getSigningKey(){
        return Keys.hmacShaKeyFor(secretKey.getBytes());
    }



    public String generateToken(String email){
        return Jwts.builder().subject(email).issuedAt(new Date()).
                expiration(new Date(System.currentTimeMillis()+expiry)).signWith(getSigningKey(), SignatureAlgorithm.HS512)
                .compact();
    }



    public String getUsernameFromToken(String token){
        return Jwts.parser().setSigningKey(getSigningKey()).build().parseClaimsJws(token).getBody().getSubject();
    }



    public boolean validateToken(String token){
        try{
            Jwts.parser().setSigningKey(getSigningKey()).build().parseClaimsJws(token);
            return true;
        }
        catch (Exception ex){
            ex.printStackTrace();
            return false;
        }
    }




}
