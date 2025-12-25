package com.zero.plantoryprojectbe.sharing;

import com.zero.plantoryprojectbe.global.security.MemberDetail;
import com.zero.plantoryprojectbe.sharing.dto.*;
import com.zero.plantoryprojectbe.sharing.service.SharingReadService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Sharing", description = "나눔 조회 API")
@RestController
@RequestMapping("/api/sharings")
@RequiredArgsConstructor
public class SharingReadRestController {

    private final SharingReadService sharingReadService;

    @Operation(
            summary = "나눔 목록 조회",
            description = "나눔 게시글 목록을 조건 검색으로 조회합니다."
    )
    @GetMapping
    public List<SharingCardListResponse> getSharingList(
            @Parameter(description = "검색 조건", example = "keyword/limit/offset 등")
            SharingSearchRequest request
    ) {
        return sharingReadService.getSharingList(request);
    }

    @Operation(
            summary = "관심 나눔 개수 조회",
            description = "로그인 사용자의 관심(찜) 나눔 개수를 조회합니다. (인증 필요)"
    )
    @GetMapping("/countInterest")
    public int countInterest(
            @Parameter(hidden = true)
            @AuthenticationPrincipal MemberDetail memberDetail
    ) {
        return sharingReadService.countInterest(memberDetail.memberResponse().getMemberId());
    }

    @Operation(
            summary = "인기 나눔 목록 조회",
            description = "인기 나눔 게시글 목록을 조건 검색으로 조회합니다."
    )
    @GetMapping("/popular")
    public List<SharingPopularResponse> getPopularSharingList(
            @Parameter(description = "검색 조건", example = "limit/offset 등")
            SharingSearchRequest request
    ) {
        return sharingReadService.getPopularSharingList(request);
    }

    @Operation(
            summary = "나눔 상세 조회",
            description = "sharingId로 나눔 상세를 조회합니다. 비로그인도 조회 가능하며 memberId는 null로 처리됩니다."
    )
    @GetMapping("/{sharingId}")
    public SelectSharingDetailResponse getSharingDetail(
            @Parameter(description = "나눔 ID", example = "100")
            @PathVariable Long sharingId,
            @Parameter(hidden = true)
            @AuthenticationPrincipal MemberDetail memberDetail
    ) {
        Long memberId = (memberDetail != null) ? memberDetail.memberResponse().getMemberId() : null;
        return sharingReadService.getSharingDetail(sharingId, memberId);
    }

    @Operation(
            summary = "나눔 댓글 목록 조회",
            description = "sharingId에 해당하는 댓글 목록을 조회합니다."
    )
    @GetMapping("/{sharingId}/comments")
    public List<SelectCommentListResponse> getSharingComments(
            @Parameter(description = "나눔 ID", example = "100")
            @PathVariable Long sharingId
    ) {
        return sharingReadService.getSharingComments(sharingId);
    }

    @Operation(
            summary = "메시지 상대 목록 조회",
            description = "특정 나눔에서 메시지를 주고받은 상대 목록을 조회합니다. (인증 필요)"
    )
    @GetMapping("/{sharingId}/partners")
    public List<SharingPartnerResponse> getMessagePartners(
            @Parameter(description = "나눔 ID", example = "100")
            @PathVariable Long sharingId,
            @Parameter(hidden = true)
            @AuthenticationPrincipal MemberDetail memberDetail
    ) {
        Long receiverId = memberDetail.memberResponse().getMemberId();
        return sharingReadService.getMessagePartners(receiverId, sharingId);
    }

    @Operation(
            summary = "나의 나눔 이력(나눔해줌) 조회",
            description = "memberId 기준으로 나눔해준 이력을 조회합니다."
    )
    @GetMapping("/history/given")
    public List<SharingHistoryResponse> getMySharingGiven(
            @Parameter(description = "회원 ID", example = "10")
            @RequestParam Long memberId
    ) {
        return sharingReadService.getMySharingGiven(memberId);
    }

    @Operation(
            summary = "나의 나눔 이력(나눔받음) 조회",
            description = "memberId 기준으로 나눔받은 이력을 조회합니다."
    )
    @GetMapping("/history/received")
    public List<SharingHistoryResponse> getMySharingReceived(
            @Parameter(description = "회원 ID", example = "10")
            @RequestParam Long memberId
    ) {
        return sharingReadService.getMySharingReceived(memberId);
    }

    @Operation(
            summary = "리뷰 작성 정보 조회",
            description = "리뷰 작성 가능 여부 및 관련 정보를 조회합니다. (인증 필요)"
    )
    @GetMapping("/{sharingId}/reviewInfo")
    public ResponseEntity<ReviewInfoResponse> getReviewInfo(
            @Parameter(description = "나눔 ID", example = "100")
            @PathVariable Long sharingId,
            @Parameter(hidden = true)
            @AuthenticationPrincipal MemberDetail memberDetail
    ) {
        Long memberId = memberDetail.memberResponse().getMemberId();
        ReviewInfoResponse result = sharingReadService.getReviewInfo(sharingId, memberId);
        return ResponseEntity.ok(result);
    }
}
