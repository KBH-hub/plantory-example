package com.zero.plantoryprojectbe.profile.service;

import com.zero.plantoryprojectbe.profile.dto.ProfileInterestListRequest;
import com.zero.plantoryprojectbe.profile.dto.ProfileSharingResponse;

import java.util.List;

public interface ProfileInterestService {
    List<ProfileSharingResponse> getProfileInterest(ProfileInterestListRequest request);
}
