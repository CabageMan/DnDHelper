package com.ironhead.dndhelper.user;

import java.util.List;

public interface UserService {
  UserResponseDto saveUser(UserRequestDto userRequest);
  UserResponseDto getUser();
  List<UserResponseDto> getAllUsers();
}
