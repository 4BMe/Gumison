package com.ssafy.gumison.security;

import com.ssafy.gumison.config.AppConfig;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class TokenProvider {

  private final AppConfig appConfig;

  public String createToken(Authentication authentication) {
    UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();

    Date now = new Date();
    Date expiryDate = new Date(now.getTime() + appConfig.getAuth().getTokenExpirationMsec());

    log.info("Create token");
    
    return Jwts.builder().setSubject(userPrincipal.getUsername()).setIssuedAt(new Date())
        .setExpiration(expiryDate).signWith(SignatureAlgorithm.HS512, appConfig.getAuth().getTokenSecret())
        .compact();
  }

  public String getUserIdFromToken(String token) {
    Claims claims =
        Jwts.parser().setSigningKey(appConfig.getAuth().getTokenSecret()).parseClaimsJws(token).getBody();

    return claims.getSubject();
  }

  public boolean validateToken(String authToken) {
    try {
      Jwts.parser().setSigningKey(appConfig.getAuth().getTokenSecret()).parseClaimsJws(authToken);
      return true;
    } catch (SignatureException ex) {
      log.error("Invalid JWT signature");
    } catch (MalformedJwtException ex) {
      log.error("Invalid JWT token");
    } catch (ExpiredJwtException ex) {
      log.error("Expired JWT token");
    } catch (UnsupportedJwtException ex) {
      log.error("Unsupported JWT token");
    } catch (IllegalArgumentException ex) {
      log.error("JWT claims string is empty.");
    }
    return false;
  }

}
