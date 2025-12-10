package com.oopsw.ejwt.jwt;

public interface JwtProperties {
    String SECRET = "oopsw"; // 우리 서버에서만 알고있는 비밀 값
    int EXPIRATION_TIME = 1000 * 60 * 60; // 60분 (1000분의 1초이므로 1000 * ~)
    String TOKEN_PREFIX = "Bearer ";
    String HEADER_STRING = "Authorization";
}
