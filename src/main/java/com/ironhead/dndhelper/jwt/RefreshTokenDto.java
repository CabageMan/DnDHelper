package com.ironhead.dndhelper.jwt;

public class RefreshTokenDto {
  private String token;

  public RefreshTokenDto() { }

  public RefreshTokenDto(String token) {
    this.token = token;
  }

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }
}
