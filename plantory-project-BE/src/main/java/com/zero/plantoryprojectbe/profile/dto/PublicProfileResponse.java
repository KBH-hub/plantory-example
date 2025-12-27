package com.zero.plantoryprojectbe.profile.dto;

import com.zero.plantoryprojectbe.member.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PublicProfileResponse {
    private Long memberId;
    private String membername;
    private String nickname;
    private String phone;
    private String address;
    private String role;
    private Boolean noticeEnabled;
    private BigDecimal sharingRate;
    private String delFlag;

    public static PublicProfileResponse from(Member member) {
        return PublicProfileResponse.builder()
                .memberId(member.getMemberId())
                .membername(member.getMembername())
                .nickname(member.getNickname())
                .phone(member.getPhone())
                .address(member.getAddress())
                .role(member.getRole() != null ? member.getRole().name() : null)
                .noticeEnabled(member.getNoticeEnabled())
                .sharingRate(
                        member.getSharingRate() != null
                                ? member.getSharingRate()
                                : BigDecimal.ZERO
                )
                .delFlag(
                        member.getDelFlag() != null
                                ? member.getDelFlag().toString()
                                : null
                )
                .build();
    }

}

