package com.zero.plantoryprojectbe.profile.service;

import com.zero.plantoryprojectbe.profile.dto.ProfileWrittenListResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProfileWrittenPageResult {
    private int total;
    private List<ProfileWrittenListResponse> list;
}

