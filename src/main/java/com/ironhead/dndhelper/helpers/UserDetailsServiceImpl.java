package com.ironhead.dndhelper.helpers;

import com.ironhead.dndhelper.user.User;
import com.ironhead.dndhelper.user.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;


/**
 * Implementation of the default SpringBoots UserDetailsService interface.
 */
@Component
public class UserDetailsServiceImpl implements UserDetailsService {

  @Autowired
  private UserRepository userRepository;

  private static final Logger logger = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

  /*
  loadUserByUsername method is called by SpringSecurity when it needs to
  retrieve user details for authentication

  When a user attempts to log in, they provide a username (or other unique identifier).
  This method is responsible for looking up the user in the user repository,
  based on this provided username.

  If the user is not found in the database it throws a standard Spring Security
  exception (UserNameNotFoundException).

  If user is found it creates a DndHelperUserDetails object - custom implementation
  of SpringSecurity UserDetails interface that wrap the user information.
   */
  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    logger.debug("Entering in loadUserByUsername method...");
    User user = userRepository.findUserByUsername(username);
    if (user == null) {
      logger.error("Username not found: " + username);
      throw new UsernameNotFoundException("Could not found user with username " + username);
    }
    logger.info("User Authenticated Successfully!");
    return new DndHelperUserDetails(user);
  }
}
