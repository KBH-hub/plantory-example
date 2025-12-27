package com.zero.plantoryprojectbe.notice;

import com.zero.plantoryprojectbe.global.security.MemberPrincipal;
import com.zero.plantoryprojectbe.notice.dto.NoticeDTO;
import com.zero.plantoryprojectbe.notice.service.NoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/notice")
@Tag(name = "Notice", description = "알림 API")
public class NoticeRestController {

    private final NoticeService noticeService;

    @Operation(summary = "알림 목록 조회", description = "로그인한 사용자의 알림 목록을 조회합니다.")
    @SecurityRequirement(name = "bearerAuth")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공"),
            @ApiResponse(responseCode = "401", description = "인증 필요"),
            @ApiResponse(responseCode = "403", description = "권한 없음")
    })
    @GetMapping
    public List<NoticeDTO> getNoticeByReceiver(
            @AuthenticationPrincipal MemberPrincipal principal
    ) {
        Long receiverId = principal.getMemberId();
        return noticeService.getNoticeByReceiver(receiverId);
    }

    @Operation(summary = "알림 등록", description = "알림을 등록합니다.")
    @SecurityRequirement(name = "bearerAuth")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "등록 성공"),
            @ApiResponse(responseCode = "400", description = "등록 실패"),
            @ApiResponse(responseCode = "401", description = "인증 필요"),
            @ApiResponse(responseCode = "403", description = "권한 없음")
    })
    @PostMapping
    public int registerNotice(
            @RequestBody NoticeDTO noticeDTO
    ) {
        return noticeService.registerNotice(noticeDTO);
    }

    @Operation(summary = "알림 읽음 처리", description = "알림을 읽음 상태로 변경합니다.")
    @SecurityRequirement(name = "bearerAuth")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "처리 성공"),
            @ApiResponse(responseCode = "400", description = "처리 실패"),
            @ApiResponse(responseCode = "401", description = "인증 필요"),
            @ApiResponse(responseCode = "403", description = "권한 없음")
    })
    @PutMapping
    public int updateNotice(
            @Parameter(description = "알림 ID", example = "10")
            @RequestParam("noticeId") Long noticeId
    ) {
        return noticeService.updateNoticeReadFlag(noticeId);
    }

    @Operation(summary = "알림 전체 삭제", description = "로그인한 사용자의 모든 알림을 삭제합니다.")
    @SecurityRequirement(name = "bearerAuth")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "삭제 성공"),
            @ApiResponse(responseCode = "401", description = "인증 필요"),
            @ApiResponse(responseCode = "403", description = "권한 없음")
    })
    @DeleteMapping
    public int removeAllNotice(
            @AuthenticationPrincipal MemberPrincipal principal
    ) {
        Long receiverId = principal.getMemberId();
        return noticeService.removeAllNotice(receiverId);
    }
}
