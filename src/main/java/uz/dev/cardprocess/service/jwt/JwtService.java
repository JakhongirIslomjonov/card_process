package uz.dev.cardprocess.service.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.*;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class JwtService {

    private final String secretKey = "bu top asfdasdfasdfasdfasdfsadfsdfasdfa123###key";

    public String genToken(UserDetails userDetails) {
        String roles = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(","));
        return "Bearer " + Jwts.builder()
                .setSubject(userDetails.getUsername())
                .claim("authorities", roles)
                .setIssuedAt(new Date())
                .setIssuer("card_process")
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 14))
                .signWith(genKey())
                .compact();
    }

    private Key genKey() {
        byte[] key = secretKey.getBytes();
        return Keys.hmacShaKeyFor(key);
    }

    public boolean isValid(String token) {
        try {
            getClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(genKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }


    public String getEmail(String token) {
        return getClaims(token).getSubject();
    }

    public List<GrantedAuthority> getAuthorities(String token) {
        String authorities = getClaims(token).get("authorities", String.class);
        return Objects.nonNull(authorities) && !authorities.isEmpty() ? Arrays.stream(authorities.split(",")).map(SimpleGrantedAuthority::new).collect(Collectors.toList()) : Collections.emptyList();
    }



}
