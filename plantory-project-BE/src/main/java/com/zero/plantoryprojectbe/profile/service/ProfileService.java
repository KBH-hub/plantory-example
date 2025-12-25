package com.zero.plantoryprojectbe.profile.service;

import com.zero.plantoryprojectbe.profile.dto.ProfileInfoResponse;
import com.zero.plantoryprojectbe.profile.dto.ProfileUpdateRequest;
import com.zero.plantoryprojectbe.profile.dto.PublicProfileResponse;

public interface ProfileService {

    ProfileInfoResponse getProfileInfo(Long memberId);

    boolean updateNoticeEnabled(Long memberId, int enabled);

    boolean updateProfileInfo(ProfileUpdateRequest request);

    boolean deleteMemberById(Long memberId);

    PublicProfileResponse getPublicProfile(Long memberId);

    boolean changePassword(Long memberId, String oldPassword, String newPassword);
}
