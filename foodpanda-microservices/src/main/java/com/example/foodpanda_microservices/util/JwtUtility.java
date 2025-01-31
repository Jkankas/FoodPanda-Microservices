package com.example.foodpanda_microservices.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;
import java.security.Key;
import java.util.Date;

@Component
public class JwtUtility {

    private final String secretKey = "shfhhfhhffdskfhksjdhfjskhdjkhfsjdhfsjkdhfsjdhdfjpipoioio1212121212pioipipoiop";
    private final long expiry = 8640000;



    private Key getSigningKey(){
        return Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    public String generateToken(String username){
        return Jwts.builder().subject(username).issuedAt(new Date()).
                expiration(new Date(System.currentTimeMillis()+expiry)).signWith(getSigningKey(),SignatureAlgorithm.HS512)
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
