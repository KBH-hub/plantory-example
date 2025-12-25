package com.zero.plantoryprojectbe.profile;

import com.zero.plantoryprojectbe.global.security.MemberDetail;
import com.zero.plantoryprojectbe.profile.dto.ProfileSharingHistoryListRequest;
import com.zero.plantoryprojectbe.profile.dto.ProfileSharingHistoryListResponse;
import com.zero.plantoryprojectbe.profile.service.ProfileSharingHistoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/profileSharing")
@Tag(name = "ProfileSharing", description = "프로필 나눔 이력 API")
public class ProfileSharingHistoryRestController {

    private final ProfileSharingHistoryService profileSharingHistoryService;

    @Operation(summary = "내 나눔 목록 조회", description = "로그인한 사용자가 등록한 나눔 목록을 조회합니다.")
    @SecurityRequirement(name = "bearerAuth")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공"),
            @ApiResponse(responseCode = "401", description = "인증 필요"),
            @ApiResponse(responseCode = "403", description = "권한 없음")
    })
    @GetMapping("/my")
    public List<ProfileSharingHistoryListResponse> getMySharing(
            @AuthenticationPrincipal MemberDetail user,
            @Parameter(description = "검색 키워드", example = "몬스테라")
            @RequestParam(required = false) String keyword,
            @Parameter(description = "상태", example = "OPEN")
            @RequestParam(required = false) String status,
            @Parameter(description = "조회 시작 위치", example = "0")
            @RequestParam int offset,
            @Parameter(description = "조회 개수", example = "10")
            @RequestParam int limit
    ) {
        ProfileSharingHistoryListRequest request = ProfileSharingHistoryListRequest.builder()
                .memberId(user.memberResponse().getMemberId())
                .keyword(keyword)
                .status(status)
                .offset(offset)
                .limit(limit)
                .build();

        return profileSharingHistoryService.getMySharingList(request);
    }

    @Operation(summary = "받은 나눔 목록 조회", description = "로그인한 사용자가 받은 나눔 목록을 조회합니다.")
    @SecurityRequirement(name = "bearerAuth")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공"),
            @ApiResponse(responseCode = "401", description = "인증 필요"),
            @ApiResponse(responseCode = "403", description = "권한 없음")
    })
    @GetMapping("/received")
    public List<ProfileSharingHistoryListResponse> getReceivedSharing(
            @AuthenticationPrincipal MemberDetail memberDetail,
            @Parameter(description = "검색 키워드", example = "나눔")
            @RequestParam(required = false) String keyword,
            @Parameter(description = "상태", example = "COMPLETED")
            @RequestParam String status,
            @Parameter(description = "조회 시작 위치", example = "0")
            @RequestParam int offset,
            @Parameter(description = "조회 개수", example = "10")
            @RequestParam int limit
    ) {
        ProfileSharingHistoryListRequest request = ProfileSharingHistoryListRequest.builder()
                .memberId(memberDetail.memberResponse().getMemberId())
                .keyword(keyword)
                .status(status)
                .offset(offset)
                .limit(limit)
                .build();

        return profileSharingHistoryService.getReceivedSharingList(request);
    }

    @Operation(summary = "프로필 나눔 카운트 조회", description = "관심 나눔 수와 완료된 나눔 수를 조회합니다.")
    @SecurityRequirement(name = "bearerAuth")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공"),
            @ApiResponse(responseCode = "401", description = "인증 필요"),
            @ApiResponse(responseCode = "403", description = "권한 없음")
    })
    @GetMapping("/counts")
    public Map<String, Integer> getProfileCounts(
            @AuthenticationPrincipal MemberDetail user
    ) {
        Long memberId = user.memberResponse().getMemberId();

        int interest = profileSharingHistoryService.getInterestCount(memberId);
        int sharing = profileSharingHistoryService.getCompletedSharingCount(memberId);

        return Map.of(
                "interestCount", interest,
                "sharingCount", sharing
        );
    }
}
