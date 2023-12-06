package com.ironhead.dndhelper.user;

import com.ironhead.dndhelper.jwt.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class UserController {

  @Autowired
  private UserService userService;

  @Autowired
  private JwtService jwtService;

  // Add RefreshToken Service
  @Autowired
  private RefreshTokenService refreshTokenService;

  @Autowired
  private AuthenticationManager authenticationManager;

  // Methods
  @PostMapping(value = "/save_user")
  public ResponseEntity<UserResponseDto> saveUser(@RequestBody UserRequestDto userRequest) {
    try {
      UserResponseDto userResponse = userService.saveUser(userRequest);
      return ResponseEntity.ok(userResponse);
    } catch (Exception e) {
      throw new RuntimeException("We get error in this method " + e.getMessage());
    }
  }

  @GetMapping("/users")
  public ResponseEntity<List<UserResponseDto>> getAllUsers() {
    try {
      List<UserResponseDto> userResponses = userService.getAllUsers();
      return ResponseEntity.ok(userResponses);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @GetMapping("/profile")
  public ResponseEntity<UserResponseDto> getUserProfile() {
    try {
      UserResponseDto userResponse = userService.getUser();
      return ResponseEntity.ok().body(userResponse); // Learn difference between wrapping into .ok and .body.
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @PostMapping("/login")
  public JwtResponseDto authenticateAndGetToken(@RequestBody AuthRequestDto authRequest) {
    Authentication authentication = authenticationManager
            .authenticate(new UsernamePasswordAuthenticationToken(
                    authRequest.getUsername(),
                    authRequest.getPassword()
            ));
    if (authentication.isAuthenticated()) {
      RefreshToken refreshToken = refreshTokenService.createRefreshToken(authRequest.getUsername());
      return new JwtResponseDto(
              jwtService.generateToken(authRequest.getUsername()),
              refreshToken.getToken()
      );
    } else {
      throw new UsernameNotFoundException("Invalid user request");
    }
  }

  @PostMapping("/refreshToken")
  public JwtResponseDto refreshToken(@RequestBody RefreshTokenDto refreshToken) {
    return refreshTokenService
            .findByToken(refreshToken.getToken())
            .map(refreshTokenService::verifyExpiration)
            .map(RefreshToken::getUser)
            .map(user ->
                    new JwtResponseDto(
                            jwtService.generateToken(user.getUsername()),
                            refreshToken.getToken()
                    )
            )
            .orElseThrow(() -> new RuntimeException("Refresh token not found"));
  }

  @PreAuthorize("hasAuthority('ROlE_ADMIN')")
  @GetMapping("/testAdmin")
  public String testAdmin() {
    try {
      return "Congrats you're an Admin";
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
