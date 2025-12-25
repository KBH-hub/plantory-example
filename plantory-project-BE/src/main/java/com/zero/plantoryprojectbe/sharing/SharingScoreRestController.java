package com.zero.plantoryprojectbe.sharing;

import com.zero.plantoryprojectbe.global.security.MemberDetail;
import com.zero.plantoryprojectbe.sharing.dto.ReviewRequest;
import com.zero.plantoryprojectbe.sharing.service.SharingScoreService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Tag(name = "SharingScore", description = "나눔 완료/리뷰 API")
@RestController
@RequestMapping("/api/sharings")
@RequiredArgsConstructor
public class SharingScoreRestController {

    private final SharingScoreService sharingScoreService;

    @Operation(
            summary = "나눔 완료 처리",
            description = "나눔 완료를 처리합니다. targetMemberId는 거래 상대 회원 ID입니다. (인증 필요)"
    )
    @PostMapping("/{sharingId}/complete")
    public ResponseEntity<?> completeSharing(
            @Parameter(description = "나눔 ID", example = "100")
            @PathVariable Long sharingId,
            @Parameter(description = "거래 상대 회원 ID", example = "25")
            @RequestParam Long targetMemberId,
            @Parameter(hidden = true)
            @AuthenticationPrincipal MemberDetail memberDetail
    ) {
        Long memberId = memberDetail.memberResponse().getMemberId();
        sharingScoreService.completeSharing(sharingId, memberId, targetMemberId);
        return ResponseEntity.ok(true);
    }

    @Operation(
            summary = "리뷰 등록",
            description = "나눔 완료 후 리뷰를 등록합니다. (인증 필요)"
    )
    @PostMapping("/{sharingId}/review")
    public ResponseEntity<?> registerReview(
            @Parameter(description = "나눔 ID", example = "100")
            @PathVariable Long sharingId,
            @RequestBody ReviewRequest reviewRequest,
            @Parameter(hidden = true)
            @AuthenticationPrincipal MemberDetail memberDetail
    ) {
        Long loginUserId = memberDetail.memberResponse().getMemberId();

        sharingScoreService.registerSharingReview(
                sharingId,
                loginUserId,
                reviewRequest.getManner(),
                reviewRequest.getReShare(),
                reviewRequest.getSatisfaction()
        );

        return ResponseEntity.ok(true);
    }
}
