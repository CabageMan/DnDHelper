package com.ironhead.dndhelper.user;

import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.List;

@Service("userService")
public class UserServiceImpl implements UserService {

  @Autowired
  UserRepository userRepository;

  ModelMapper modelMapper = new ModelMapper();

  /**
   * Learn what refresh(savedUser) does.
   * @param userRequest
   * @return
   */
  @Override
  public UserResponseDto saveUser(UserRequestDto userRequest) {
    if (userRequest.getUsername() == null) {
      throw new RuntimeException("Parameter username is not found in request");
    } else if (userRequest.getPassword() == null) {
      throw new RuntimeException("Parameter username is not found in request");
    }

    UserInfo savedUser = null;

    var encoder = new BCryptPasswordEncoder();
    String rawPassword = userRequest.getPassword();
    String encodedPassword = encoder.encode(rawPassword);

    UserInfo user = modelMapper.map(userRequest, UserInfo.class);
    user.setPassword(encodedPassword);

    if (userRequest.getId() != null) {
      UserInfo existedUser = userRepository.findFirstById(userRequest.getId());
      if (existedUser != null) {
        existedUser.setId(user.getId());
        existedUser.setUsername(user.getUsername());
        existedUser.setPassword(user.getPassword());
        existedUser.setRoles(user.getRoles());
        savedUser = userRepository.save(existedUser);
        userRepository.refresh(savedUser);
      } else {
        throw new EntityNotFoundException("Could not find user with identifier: " + userRequest.getId());
      }
    } else {
      savedUser = userRepository.save(user);
    }

    userRepository.refresh(savedUser);
    return modelMapper.map(savedUser, UserResponseDto.class);
  }

  @Override
  public UserResponseDto getUser() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    UserDetails userDetails = (UserDetails) authentication.getPrincipal();
    String userNameFromToken = userDetails.getUsername();
    UserInfo user = userRepository.findUserByUsername(userNameFromToken);
    return modelMapper.map(user, UserResponseDto.class);
  }

  @Override
  public List<UserResponseDto> getAllUsers() {
    List<UserInfo> users = (List<UserInfo>) userRepository.findAll();
    Type setOfDTOsTypes = new TypeToken<List<UserResponseDto>>() {}.getType();
    return modelMapper.map(users, setOfDTOsTypes);
  }
}
