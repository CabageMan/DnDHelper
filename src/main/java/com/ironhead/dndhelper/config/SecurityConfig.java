package com.ironhead.dndhelper.config;

import com.ironhead.dndhelper.helpers.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
  public static final String REQUEST_ENDPOINT_BEGINNING = "/api/v1/";
//  public static final String REQUEST_ENDPOINT_COMMON = REQUEST_ENDPOINT_BEGINNING + "**";
  public static final String SAVE_REQUEST_ENDPOINT = REQUEST_ENDPOINT_BEGINNING + "save";
  public static final String LOGIN_REQUEST_ENDPOINT = REQUEST_ENDPOINT_BEGINNING + "login";
  public static final String REFRESH_TOKEN_REQUEST_ENDPOINT = REQUEST_ENDPOINT_BEGINNING + "refreshToken";

  @Autowired
  JwtAuthFilter jwtAuthFilter;

  /**
   * Defines the security settings, authentication and authorization rules for the application.
   * @param http HttpSecurity
   * @return SecurityFilterChain
   * @throws Exception
   */
  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    /*
    Disable Cross-Site Request Forgery protection
    Permit public access to specific endpoints: save, login, refreshToken
    For other endpoints it requires authentication
    The session management is set to SessionCreationPolicy.STATELESS, indicating that the application
    will be stateless, and sessions are not used for user tracking. This is common in JWT-based authentication.
     */
    return http
            .csrf(AbstractHttpConfigurer::disable) // Also possible declare as lambda .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(authz -> authz
                    .requestMatchers(SAVE_REQUEST_ENDPOINT, LOGIN_REQUEST_ENDPOINT, REFRESH_TOKEN_REQUEST_ENDPOINT)
                    .permitAll()
                    .anyRequest()
                    .authenticated()
            )
            .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authenticationProvider(authenticationProvider())
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
            .build();
  }

  @Bean
  public AuthenticationProvider authenticationProvider() {
    DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
    authenticationProvider.setUserDetailsService(userDetailsService());
    authenticationProvider.setPasswordEncoder(passwordEncoder());
    return authenticationProvider;
  }

  @Bean
  public UserDetailsService userDetailsService() {
    return new UserDetailsServiceImpl();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public AuthenticationManager authenticationManager(
          AuthenticationConfiguration configuration
  ) throws Exception {
    return configuration.getAuthenticationManager();
  }
}
