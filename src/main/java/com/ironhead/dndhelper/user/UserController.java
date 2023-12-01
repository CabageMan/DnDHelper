package com.ironhead.dndhelper.user;

import com.ironhead.dndhelper.jwt.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class UserController {

  @Autowired
  private UserService userService;

  @Autowired
  private JwtService jwtService;

  // Add RefreshToken Service

  @Autowired
  private AuthenticationManager authenticationManager;

  // Methods
  @PostMapping(value = "/save")
  public ResponseEntity saveUser(@RequestBody UserRequestDto userRequest) {
    try {
      UserResponseDto userResponse = userService.saveUser(userRequest);
      return ResponseEntity.ok(userResponse);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  // Fix: java.lang.IllegalArgumentException: Attempt to generate refresh event with null object
  // at com.ironhead.dndhelper.helpers.RefreshableCrudRepositoryImpl.refresh(RefreshableCrudRepositoryImpl.java:27)
}
