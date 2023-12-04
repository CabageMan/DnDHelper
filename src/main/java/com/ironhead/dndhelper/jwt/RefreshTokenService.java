package com.ironhead.dndhelper.jwt;

import com.ironhead.dndhelper.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class RefreshTokenService {

  // Move this to environment
  public static final long EXPIRED_TIME = 600_000;

  @Autowired
  RefreshTokenRepository refreshTokenRepository;

  @Autowired
  UserRepository userRepository;

  public RefreshToken createRefreshToken(String userName) {
    RefreshToken refreshToken = new RefreshToken(
            UUID.randomUUID().toString(),
            Instant.now().plusMillis(EXPIRED_TIME),
            userRepository.findUserByUsername(userName)
    );
    return refreshTokenRepository.save(refreshToken);
  }

  public Optional<RefreshToken> findByToken(String token) {
    return refreshTokenRepository.findByToken(token);
  }

  public RefreshToken verifyExpiration(RefreshToken token) {
    if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
      refreshTokenRepository.delete(token);
      throw new RuntimeException(token.getToken() + ": Refresh token is expired, make login again.");
    }
    return token;
  }
}
