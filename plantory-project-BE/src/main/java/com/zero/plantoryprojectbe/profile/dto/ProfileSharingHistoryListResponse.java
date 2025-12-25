package com.zero.plantoryprojectbe.profile.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProfileSharingHistoryListResponse {
    private Long sharingId;
    private Long targetMemberId;
    private String title;
    private String status;
    private Integer interestNum;
    private LocalDateTime createdAt;
    private LocalDateTime reviewFlag;
    private LocalDateTime targetMemberReviewFlag;
    private Integer commentCount;
    private String thumbnail;
    private int totalCount;
}
