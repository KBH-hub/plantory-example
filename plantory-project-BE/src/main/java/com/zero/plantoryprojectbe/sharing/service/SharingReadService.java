package com.zero.plantoryprojectbe.sharing.service;

import com.zero.plantoryprojectbe.sharing.dto.*;

import java.util.List;

public interface SharingReadService {
    List<SharingCardListResponse> getSharingList(SharingSearchRequest request);

    int countInterest(Long memberId);

    List<SharingPopularResponse> getPopularSharingList(SharingSearchRequest request);

    SelectSharingDetailResponse getSharingDetail(Long sharingId, Long memberId);

    List<SelectCommentListResponse> getSharingComments(Long sharingId);

    List<SharingPartnerResponse> getMessagePartners(Long receiverId, Long sharingId);

    List<SharingHistoryResponse> getMySharingGiven(Long memberId);

    List<SharingHistoryResponse> getMySharingReceived(Long memberId);

    ReviewInfoResponse getReviewInfo(Long sharingId, Long memberId);
}
