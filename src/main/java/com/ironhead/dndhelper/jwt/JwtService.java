package com.ironhead.dndhelper.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtService {

  /**
   * A secret cryptographic key used to sign and verify JWTs.
   */
  @Value("${security.jwt.token.secret-key}")
  private String jwt_secret;

  /**
   * Checks whether the extracted userName from token matches the userName
   * of the provided UserDetails object, and verifies whether the token has expired.
   * @param token String
   * @param userDetails UserDetails
   * @return Boolean
   */
  public Boolean validateToken(String token, UserDetails userDetails) {
    final String userName = extractUserName(token);
    return (userName.equals(userDetails.getUsername())) && !isTokenExpired(token);
  }

  /**
   * Checks whether a JWT token has expired by comparing the token's expiration date
   * to the current date.
   * @param token String
   * @return Boolean
   */
  public Boolean isTokenExpired(String token) {
    return extractExpiration(token).before(new Date());
  }

  /**
   * Generates a JWT token based on a username as input.
   * @param userName String
   * @return String
   */
  public String generateToken(String userName) {
    Map<String, Object> claims = new HashMap<>();
    return createToken(claims, userName);
  }

  /**
   * Extract the userName subject from the tokens claims.
   * @param token String
   * @return String
   */
  public String extractUserName(String token) {
    return extractClaim(token, Claims::getSubject);
  }

  /**
   * Extract the expiration date from the JWT token's claims.
   * @param token String
   * @return Date
   */
  public Date extractExpiration(String token) {
    return extractClaim(token, Claims::getExpiration);
  }

  /**
   * Extracts a specific claim from the JWT token's claims by using generic
   * function that specifies how to extract desired claim.
   * @param token String
   * @param claimsResolver Function<Claims, T>
   * @return T
   * @param <T>
   */
  public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
    final Claims claims = extractAllClaims(token);
    return claimsResolver.apply(claims);
  }

  /**
   * Creates the JWT token by using JWTs builder to specify the claims, subject,
   * issue date, expiration date, signing key.
   * @param claims MAP<String, Object>
   * @param userName String
   * @return String
   */
  private String createToken(Map<String, Object> claims, String userName) {
    return Jwts
            .builder()
            .setClaims(claims)
            .setSubject(userName)
            .setIssuedAt(createIssuedDate())
            .setExpiration(createExpirationDate())
            .signWith(getSignKey(), SignatureAlgorithm.HS256)
            .compact();
  }

  /**
   * Parses the JWT token and extract all of its claims by using Jwts builder
   * to create a parser that is configured with the appropriate singing key.
   * @param token
   * @return
   */
  private Claims extractAllClaims(String token) {
    return Jwts
            .parserBuilder()
            .setSigningKey(getSignKey())
            .build()
            .parseClaimsJws(token)
            .getBody();
  }

  /**
   * Obtains the signing key  for JWT token creation and validation by decoding
   * the jwt_secret key and converting it into a cryptographic key.
    * @return Key
   */
  private Key getSignKey() {
    byte[] keyBytes = Decoders.BASE64.decode(jwt_secret);
    return Keys.hmacShaKeyFor(keyBytes);
  }

  private Date createIssuedDate() {
    return new Date(System.currentTimeMillis());
  }

  private Date createExpirationDate() {
    // A hour
    return new Date(System.currentTimeMillis() + 1000 * 60 * 60);
  }
}
