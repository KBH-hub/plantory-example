package com.zero.plantoryprojectbe.profile.dto;

import lombok.Data;

@Data
public class ProfileInterestListRequest {
    private Long memberId;
    private String keyword;
    private Integer offset;
    private Integer limit;
}

