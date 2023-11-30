package com.ironhead.dndhelper.config;

import com.ironhead.dndhelper.jwt.JwtService;
import com.ironhead.dndhelper.user.UserDetailsServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {
  public static final String AUTHORIZATION_STRING = "Authorization";
  public static final String BEARER_STRING = "Bearer ";

  @Autowired
  private JwtService jwtService;

  @Autowired
  UserDetailsServiceImpl userDetailsService;

  /**
   * doFilterInternal gets invoked for every request to application.
   * It's responsible for processing incoming requests by inspecting the "Authorization" header
   * to identify and validate a "Bearer" token
   *
   * @param request     HttpServletRequest
   * @param response    HttpServletResponse
   * @param filterChain filterChain
   * @throws ServletException
   * @throws IOException
   */
  @Override
  protected void doFilterInternal(
          HttpServletRequest request,
          HttpServletResponse response,
          FilterChain filterChain
  ) throws ServletException, IOException {
    String authHeader = request.getHeader(AUTHORIZATION_STRING);
    String token = null;
    String username = null;

    /*
    Requests that are not related to user login typically do not have a JWT token in their headers,
    so they pass through to the next filter chain without any token-related processing.
     */
    if (authHeader != null && authHeader.startsWith(BEARER_STRING)) {
      token = authHeader.substring(BEARER_STRING.length()); // Ignore "Bearer "
      System.out.println("\n Bearer String length = " + BEARER_STRING.length() + "\n");
      username = jwtService.extractUserName(token);
    }

    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authHeader != null && authentication == null) {
      UserDetails userDetails = userDetailsService.loadUserByUsername(username);
      if (jwtService.validateToken(token, userDetails)) {
        /*
        If the token is valid, the filter authenticates the request by creating an Authentication object.
        This object represents the user's authentication status and contains information about the user,
        such as their username and authorities.
        The authenticated user is then stored in the SecurityContext, ensuring they have access to
        protected resources in the application.
         */
        var authenticationToken = new UsernamePasswordAuthenticationToken(
                userDetails,
                null,
                userDetails.getAuthorities()
        );
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
      }
    }

    filterChain.doFilter(request, response);
  }
}
