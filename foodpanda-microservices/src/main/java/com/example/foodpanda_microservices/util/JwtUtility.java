package com.example.foodpanda_microservices.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.security.Key;
import java.util.Date;

@Component
public class JwtUtility {

    @Autowired
    Logger log;


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
            log.info(" Claimed Payload,{}",Jwts.parser().setSigningKey(getSigningKey()).build().parseClaimsJws(token));
            return true;
        }
        catch (Exception ex){
            ex.printStackTrace();
            return false;
        }
    }



    // TO BE USED LATER
    public String validateCustomerToken(String token){
        try{
            Jwts.parser().setSigningKey(getSigningKey()).build().parseClaimsJws(token);
            Jws<Claims> body = Jwts.parser().setSigningKey(getSigningKey()).build().parseClaimsJws(token);
          String mobile = body.getPayload().getSubject();
            log.info("Claimed Payload,{}",mobile);
            return mobile;
        }
        catch (Exception ex){
            ex.printStackTrace();
            return null;
        }
    }

}
