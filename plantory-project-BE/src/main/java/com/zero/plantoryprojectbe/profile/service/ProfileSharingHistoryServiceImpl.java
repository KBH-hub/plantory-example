package com.zero.plantoryprojectbe.profile.service;

import com.zero.plantoryprojectbe.profile.ProfileSharingHistoryMapper;
import com.zero.plantoryprojectbe.profile.dto.ProfileSharingHistoryListRequest;
import com.zero.plantoryprojectbe.profile.dto.ProfileSharingHistoryListResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProfileSharingHistoryServiceImpl implements ProfileSharingHistoryService {

    private final ProfileSharingHistoryMapper profileSharingHistoryMapper;

    @Override
    public int getInterestCount(Long memberId) {
        return profileSharingHistoryMapper.countByInterestCount(memberId);
    }

    @Override
    public int getCompletedSharingCount(Long memberId) {
        return profileSharingHistoryMapper.countByCompletedSharingCount(memberId);
    }

    @Override
    public  List<ProfileSharingHistoryListResponse> getMySharingList(ProfileSharingHistoryListRequest request) {
        if (request.getKeyword() != null && request.getKeyword().isBlank()) {
            request.setKeyword(null);
        }
        if (request.getStatus() != null && request.getStatus().isBlank()) {
            request.setStatus(null);
        }

        List<ProfileSharingHistoryListResponse> list;
        list = profileSharingHistoryMapper.selectMySharingList(request);
        return list;
    }

    @Override
    public  List<ProfileSharingHistoryListResponse> getReceivedSharingList(ProfileSharingHistoryListRequest request) {if (request.getKeyword() != null && request.getKeyword().isBlank()) {
        request.setKeyword(null);
    }
        if (request.getStatus() != null && request.getStatus().isBlank()) {
            request.setStatus(null);
        }


        List<ProfileSharingHistoryListResponse> list;
        list = profileSharingHistoryMapper.selectReceivedSharingList(request);
        return list;
    }

}
