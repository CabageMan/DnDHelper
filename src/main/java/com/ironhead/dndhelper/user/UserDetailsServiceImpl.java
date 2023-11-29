package com.ironhead.dndhelper.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {

  @Autowired
  private UserRepository userRepository;

  private static final Logger logger = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    logger.debug("Entering in loadUserByUsername method...");
    User user = userRepository.findUserByUsername(username);
    if (user == null) {
      logger.error("Username not found: " + username);
      throw new UsernameNotFoundException("Could not found user with username " + username );
    }
    logger.info("User Authenticated Successfully!");
    return new CustomUserDetails();
  }
}
