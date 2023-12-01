package com.ironhead.dndhelper.helpers;

import com.ironhead.dndhelper.user.User;
import com.ironhead.dndhelper.user.UserRole;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Implementation of the default SpringBoots UserDetails interface.
 */
public class DndHelperUserDetails extends User implements UserDetails {

  private String username;
  private String password;
  Collection<? extends GrantedAuthority> authorities;

  public DndHelperUserDetails(User user) {
    this.username = user.getUsername();
    this.password = user.getPassword();

    List<GrantedAuthority> auths = new ArrayList<>();
    for (UserRole role : user.getRoles()) {
      auths.add(new SimpleGrantedAuthority(role.getName().toUpperCase()));
    }
    this.authorities = auths;
  }

  /**
   * Read about best practices of this method implementation.
   * @return Collection<? extends GrantedAuthority>
   */
  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return authorities;
  }

  /**
   * Read about best practices of this method implementation.
   * @return String
   */
  @Override
  public String getUsername() {
    return username;
  }

  /**
   * Read about best practices of this method implementation.
   * @return String
   */
  @Override
  public String getPassword() {
    return password;
  }

  /**
   * Read about best practices of this method implementation.
   * @return boolean
   */
  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  /**
   * Read about best practices of this method implementation.
   * @return boolean
   */
  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  /**
   * Read about best practices of this method implementation.
   * @return boolean
   */
  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  /**
   * Read about best practices of this method implementation.
   * @return boolean
   */
  @Override
  public boolean isEnabled() {
    return true;
  }
}
