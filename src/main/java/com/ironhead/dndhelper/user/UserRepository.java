package com.ironhead.dndhelper.user;

import com.ironhead.dndhelper.helpers.RefreshableCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends RefreshableCrudRepository<User, Long> {
  User findUserByUsername(String username);
  User findFirstById(Long id);
}
