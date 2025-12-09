package com.oopsw.ejwt.repository;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 단일 테이블 사용하므로 따로 네이밍 X // 자동
    private String username; // 이름은 중복 가능
    private String password;
    private String email; // 유니크 지정 X -> 오류 안남 (현실적으로 유니크 값이 아닐 수 있음)
    private String role; // ROLE_USER, ROLE_ADMIN ...
    // role : 들어온 ui 루트(or 관리자) 보고 시스템이 결정(입력하는 사용자가 결정 불가) => set 메서드 활성화되어있어야 함
    @CreationTimestamp
    private Timestamp createdAt;
}
