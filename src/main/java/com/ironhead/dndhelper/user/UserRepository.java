package com.ironhead.dndhelper.user;

import com.ironhead.dndhelper.helpers.RefreshableCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends RefreshableCrudRepository<UserInfo, Long> {
  UserInfo findUserByUsername(String username);
  UserInfo findFirstById(Long id);

  boolean existsByUsername(String username);
}
