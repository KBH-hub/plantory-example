package com.zero.plantoryprojectbe.sharing.dto;

import com.zero.plantoryprojectbe.sharing.service.SharingScoreServiceImpl;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewInfoResponse {
    private Long sharingId;
    private Long partnerId;
    private String partnerNickname;
    private String title;
    private LocalDateTime createdAt;
    private SharingScoreServiceImpl.ReviewerType reviewerType;
}