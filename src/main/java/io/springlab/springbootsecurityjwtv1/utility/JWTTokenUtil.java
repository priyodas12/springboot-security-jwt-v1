package io.springlab.springbootsecurityjwtv1.utility;

import org.springframework.beans.factory.annotation.Value;
import io.jsonwebtoken.*;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JWTTokenUtil implements Serializable {

    @Value("${jwt.secret}")
    private String secret;

    private long initialTime=System.currentTimeMillis();
    private long expireTime=System.currentTimeMillis()+1000*60;

    public String getUsernameFromToken(String token){
        return getClaimsFromToken(token,Claims::getSubject);
    }

    public Date getExpirationDateFromToken(String token){
        return getClaimsFromToken(token,Claims::getExpiration);
    }

    public Boolean isTokenExpired(String token){
        final Date expireDate=getExpirationDateFromToken(token);
        return expireDate.before(new Date());
    }

    public <T> T getClaimsFromToken(String token, Function<Claims,T> claimsResolver){
        Claims claims=getAllClaims(token);
        return claimsResolver.apply(claims);
    }

    //get all claims
    private Claims getAllClaims(String token){
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    //public expose generate token
    public String generateJWTToken(UserDetails userDetails){
        Map<String,Object> claims=new HashMap<>();
        return generateToken(claims, userDetails.getUsername());
    }

    //private generate token end point
    private String generateToken(Map<String, Object> claims,String subject){
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(initialTime))
                .setExpiration(new Date(expireTime))
                .signWith(SignatureAlgorithm.HS512,secret)
                .compact();
    }

    public Boolean validateToken(String token ,UserDetails userDetails){
        final String username=getUsernameFromToken(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

}
