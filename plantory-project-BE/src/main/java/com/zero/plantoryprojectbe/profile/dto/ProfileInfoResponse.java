package com.zero.plantoryprojectbe.profile.dto;

import com.zero.plantoryprojectbe.member.Member;
import com.zero.plantoryprojectbe.member.Role;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
public class ProfileInfoResponse {

    private final Long memberId;
    private final String membername;
    private final String nickname;
    private final String phone;
    private final String address;
    private final Role role;
    private final Boolean noticeEnabled;
    private final BigDecimal sharingRate;
    private final LocalDateTime delFlag;

    private ProfileInfoResponse(
            Long memberId,
            String membername,
            String nickname,
            String phone,
            String address,
            Role role,
            Boolean noticeEnabled,
            BigDecimal sharingRate,
            LocalDateTime delFlag
    ) {
        this.memberId = memberId;
        this.membername = membername;
        this.nickname = nickname;
        this.phone = phone;
        this.address = address;
        this.role = role;
        this.noticeEnabled = noticeEnabled;
        this.sharingRate = sharingRate;
        this.delFlag = delFlag;
    }

    public static ProfileInfoResponse from(Member member) {
        return new ProfileInfoResponse(
                member.getMemberId(),
                member.getMembername(),
                member.getNickname(),
                member.getPhone(),
                member.getAddress(),
                member.getRole(),
                member.getNoticeEnabled(),
                member.getSharingRate() != null
                        ? member.getSharingRate()
                        : BigDecimal.ZERO,
                member.getDelFlag()
        );
    }
}