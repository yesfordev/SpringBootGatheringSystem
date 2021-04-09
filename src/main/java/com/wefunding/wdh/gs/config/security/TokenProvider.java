package com.wefunding.wdh.gs.config.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
@Component
@Slf4j
public class TokenProvider {

    @Value("${spring.jwt.secret}")
    private String secretKey;

    private long tokenValidTime = 1000L * 60 * 10;

    private final UserDetailsService userDetailsService;

    public String createToken(String userPk, List<String> roles) {

        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(secretKey);

        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        Key KEY = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

        Claims claims = Jwts.claims().setSubject(userPk);
        claims.put("roles", roles);
        Date now = new Date();

        String result = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + tokenValidTime))
                .signWith(KEY, SignatureAlgorithm.HS256)
                .compact();
        log.info("createToken : "  +  result);

        return  result;

    }


    public Authentication getAuthentication(String token) {
        log.info("getAuthentication");
        UserDetails userDetails = userDetailsService.loadUserByUsername(this.getUser(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public String getUser(String token) {
        log.info("getUser");
        return Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody().getSubject();
    }

    public String resolveToken(HttpServletRequest req) {
        log.info("resolveToken");
        return req.getHeader("X-AUTH-TOKEN");
    }

    public boolean validateToken(String jwtToken) {
        log.info("validateToken");
        try {
            Jws<Claims> claims = Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(jwtToken);
            boolean sad = !claims.getBody().getExpiration().before(new Date());
            log.info("validateToken : " + sad);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }
}



