package com.ghada.authentificationjwt.config;

import com.ghada.authentificationjwt.model.UserDTO;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class JwtTokenUtil implements Serializable {
    public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;
    @Value("${jwt.secret}")
    private String secret;

    public Integer getSpaceIdFromToken(String token ) {
        final Claims claims = getAllClaimsFromToken( token);
        return (Integer) claims.get("spaceId");

    }
    //retrieve username from jwt token
    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    //retrieve expiration date from jwt token
    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims =getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }
    //for retrieveing any information from token we will need the secret key
    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public List<Long> getSpaceListFromToken(String token) {
        final Claims claims = getAllClaimsFromToken(token);
        ArrayList<Integer> spaceList = (ArrayList)claims.get("spacesList");
        return spaceList.stream().map(Integer :: longValue).collect(Collectors.toList());
    }

    // check if the token has expired or not
    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    //generate token for user
    public String generateToken(UserDTO user) {
        return doGenerateToken(user);
    }

    private String doGenerateToken(UserDTO	 user) {

        Claims claims = Jwts.claims().setSubject(user.getLogin());
        claims.put("spaceId", user.getSpaceId()) ;
        claims.put("spacesList", user.getSpacesList()) ;

        return Jwts.builder()
                .setClaims(claims)
                .setIssuer("auth-server")
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + (JWT_TOKEN_VALIDITY * 1000)))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    //validate token

    public Boolean validateToken(String token, UserDetails userDetails) {
        try {
            if(isTokenExpired(token)) {
                return false  ;
            }else {
                final String username = getUsernameFromToken(token);
                return  username.equals(userDetails.getUsername()) ;

            }
        }catch (ExpiredJwtException e ) {
            return false ;
        }


    }


}
