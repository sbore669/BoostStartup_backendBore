package com.CrowdfundingSoutenance.CrowdfundingSout.security.jwt;

import com.CrowdfundingSoutenance.CrowdfundingSout.security.services.UtilisateursDetails;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.SignatureException;
import java.util.Date;

@Component
public class JwtUtils {

  private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

  @Value("${bore.app.jwtSecret}")
  private String jwtSecret;

  @Value("${bore.app.jwtExpirationMs}")
  private int jwtExpirationMs;

  public String generateJwtToken(Authentication authentication) {

    //recupere les details de l'user actuel depuis autentication passer en parametre
    UtilisateursDetails userPrincipal = (UtilisateursDetails) authentication.getPrincipal();

    //generation du token
    return Jwts.builder()
        .setSubject((userPrincipal.getUsername()))
        .setIssuedAt(new Date())
        .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
        .signWith(SignatureAlgorithm.HS512, jwtSecret)
        .compact();//HS256
  }

  //recupere et retourne username de l'utilisateur à partir du token
  public String getUserNameFromJwtToken(String token) {

    //retourne le nom de l'utilisateur
    return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
  }


  //verifie la validité du JWT et tourne true si le token est validé sinon une exception
  public boolean validateJwtToken(String authToken) {
    try {
      //verifie la validité du JWT
      Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
      return true;
    } catch (MalformedJwtException e) {
      logger.error("Jeton JWT non valide: {}", e.getMessage());
    } catch (ExpiredJwtException e) {
      logger.error("Le jeton JWT a expiré: {}", e.getMessage());
    } catch (UnsupportedJwtException e) {
      logger.error("Le jeton JWT n'est pas pris en charge: {}", e.getMessage());
    } catch (IllegalArgumentException e) {
      logger.error("La chaîne de revendications JWT est vide: {}", e.getMessage());
    }

    return false;
  }
}
