package com.zero.plantoryprojectbe.profile.service;

import com.zero.plantoryprojectbe.profile.dto.ProfileSharingHistoryListRequest;
import com.zero.plantoryprojectbe.profile.dto.ProfileSharingHistoryListResponse;

import java.util.List;

public interface ProfileSharingHistoryService {

    int getInterestCount(Long memberId);

    int getCompletedSharingCount(Long memberId);

List<ProfileSharingHistoryListResponse> getMySharingList(ProfileSharingHistoryListRequest request);
List<ProfileSharingHistoryListResponse> getReceivedSharingList(ProfileSharingHistoryListRequest request);
}