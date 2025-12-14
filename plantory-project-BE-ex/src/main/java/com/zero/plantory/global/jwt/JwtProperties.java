package com.zero.plantory.global.jwt;

public interface JwtProperties {
    String SECRET = "plantory";
    int EXPIRATION_TIME = 1000*60*60; //1시간
    String TOKEN_PREFIX = "Bearer ";
    String HEADER_STRING = "Authorization";
}
