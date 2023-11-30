package com.ironhead.dndhelper.jwt;

public class JwtResponseDto {
  private String accessToken;
  private String token;

  public JwtResponseDto() { }

  public JwtResponseDto(String accessToken, String token) {
    this.accessToken = accessToken;
    this.token = token;
  }

  public String getAccessToken() {
    return accessToken;
  }

  public void setAccessToken(String accessToken) {
    this.accessToken = accessToken;
  }

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }
}
