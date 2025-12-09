package com.oopsw.ejwt.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserVO {
    private Long id; // 단일 테이블 사용하므로 따로 네이밍 X // 자동
    private String username;
    private String password; // 원래 노출하면 안됨. 실습 목적
    private String email;
    private String role; // ROLE_USER, ROLE_ADMIN ...
    private Timestamp createdAt;
}
