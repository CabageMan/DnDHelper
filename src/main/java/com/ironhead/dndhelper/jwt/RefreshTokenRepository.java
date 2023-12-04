package com.ironhead.dndhelper.jwt;

import com.ironhead.dndhelper.helpers.RefreshableCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends RefreshableCrudRepository<RefreshToken, Long> {
  Optional<RefreshToken> findByToken(String token);
}
