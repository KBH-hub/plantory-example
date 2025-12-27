package com.zero.plantoryprojectbe.member;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "member")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long memberId;
    @Column(name = "membername", nullable = false)
    private String membername;
    @Column(nullable = false)
    private String password;
    private String nickname;
    private String phone;
    private String address;
    private BigDecimal sharingRate;
    private BigDecimal skillRate;
    private BigDecimal managementRate;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;
    private LocalDateTime stopDay;
    private LocalDateTime delFlag;
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;
    private Boolean noticeEnabled;

    public static Member createForSignUp(
            String membername,
            String encodedPassword,
            String nickname,
            String phone,
            String address
    ) {
        Member m = new Member();
        m.membername = membername;
        m.password = encodedPassword;
        m.nickname = nickname;
        m.phone = phone;
        m.address = address;

        m.sharingRate = BigDecimal.valueOf(7);
        m.skillRate = BigDecimal.ZERO;
        m.managementRate = BigDecimal.ZERO;
        m.role = Role.USER;
        m.noticeEnabled = true;

        return m;
    }

    public void changePassword(String encodedPassword) {
        if (encodedPassword == null || encodedPassword.isBlank()) {
            throw new IllegalArgumentException("encodedPassword는 빈값을 넣을 수 없습니다");
        }
        this.password = encodedPassword;
    }

    public void updateNoticeEnabled(boolean enabled) {
        this.noticeEnabled = enabled;
    }

    public void updateProfileInfo(String nickname, String phone, String address) {
        this.nickname = nickname;
        this.phone = phone;
        this.address = address;
    }

    public void softDeleteNow() {
        this.delFlag = LocalDateTime.now(); // delFlag가 LocalDateTime인 경우
    }

    public static Member create(String membername, String password) {
        Member m = new Member();
        m.membername = membername;
        m.password = password;
        m.role = Role.USER;
        m.noticeEnabled = true;
        return m;
    }

    public void resetStopDay() {
        this.stopDay = null;
    }

    @PrePersist
    public void prePersist() {
        if (this.createdAt == null) {
            this.createdAt = LocalDateTime.now();
        }
    }

}
