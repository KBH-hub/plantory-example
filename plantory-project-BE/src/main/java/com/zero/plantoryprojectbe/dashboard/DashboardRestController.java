package com.zero.plantoryprojectbe.dashboard;

import com.zero.plantoryprojectbe.dashboard.dto.DashboardSummaryResponse;
import com.zero.plantoryprojectbe.dashboard.service.DashboardService;
import com.zero.plantoryprojectbe.global.security.MemberDetail;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
@Tag(name = "Dashboard", description = "대시보드 정보 API")
public class DashboardRestController {

    private final DashboardService dashboardService;

    @Operation(
            summary = "대시보드 조회",
            description = "로그인한 사용자의 대시보드 정보를 조회합니다. (활동 요약, 통계 정보 등)"
    )
    @SecurityRequirement(name = "bearerAuth")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공"),
            @ApiResponse(responseCode = "401", description = "인증 필요(JWT 누락 또는 만료)"),
            @ApiResponse(responseCode = "403", description = "권한 없음")
    })
    @GetMapping
    public DashboardSummaryResponse getDashboard(
            @AuthenticationPrincipal MemberDetail memberDetail
    ) {
        return dashboardService.getDashboardSummary(
                memberDetail.memberResponse().getMemberId()
        );
    }
}
