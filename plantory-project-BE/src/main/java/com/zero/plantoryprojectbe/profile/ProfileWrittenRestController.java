package com.zero.plantoryprojectbe.profile;

import com.zero.plantoryprojectbe.profile.dto.ProfileWrittenDeleteRequest;
import com.zero.plantoryprojectbe.profile.dto.ProfileWrittenListRequest;
import com.zero.plantoryprojectbe.profile.service.ProfileContentService;
import com.zero.plantoryprojectbe.profile.service.ProfileWrittenPageResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/profileWritten")
@Tag(name = "ProfileWritten", description = "프로필 작성 글 API")
public class ProfileWrittenRestController {

    private final ProfileContentService profileContentService;

    @Operation(summary = "프로필 작성 글 목록 조회", description = "회원이 작성한 글 목록을 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공")
    })
    @GetMapping("/{memberId}")
    public ResponseEntity<ProfileWrittenPageResult> getWrittenList(
            @Parameter(description = "회원 ID", example = "8")
            @PathVariable Long memberId,
            @Parameter(description = "검색 키워드", example = "몬스테라")
            @RequestParam(defaultValue = "") String keyword,
            @Parameter(description = "카테고리", example = "ALL")
            @RequestParam(defaultValue = "ALL") String category,
            @Parameter(description = "조회 개수", example = "10")
            @RequestParam(defaultValue = "10") int limit,
            @Parameter(description = "조회 시작 위치", example = "0")
            @RequestParam(defaultValue = "0") int offset
    ) {
        ProfileWrittenListRequest profileContentServiceProfileWrittenList =
                ProfileWrittenListRequest.builder()
                        .memberId(memberId)
                        .keyword(keyword)
                        .limit(limit)
                        .offset(offset)
                        .build();

        return ResponseEntity.ok(
                profileContentService.getProfileWrittenList(
                        profileContentServiceProfileWrittenList,
                        category
                )
        );
    }

    @Operation(summary = "프로필 작성 글 삭제", description = "프로필에서 작성한 글을 소프트 삭제합니다.")
    @SecurityRequirement(name = "bearerAuth")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "삭제 성공"),
            @ApiResponse(responseCode = "401", description = "인증 필요"),
            @ApiResponse(responseCode = "403", description = "권한 없음")
    })
    @PostMapping("/softDelete")
    public ResponseEntity<?> deleteWritten(
            @RequestBody ProfileWrittenDeleteRequest request
    ) {
        profileContentService.deleteWritten(request);
        return ResponseEntity.ok().build();
    }
}
