package com.zero.plantoryprojectbe.memberManagement;

import com.zero.plantoryprojectbe.memberManagement.dto.MemberManagementResponse;
import com.zero.plantoryprojectbe.memberManagement.service.MemberManagementService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/memberManagement")
@Tag(name = "MemberManagement", description = "회원 관리(관리자) API")
public class MemberManagementRestController {

    private final MemberManagementService memberManagementService;

    @Operation(summary = "회원 목록 조회", description = "관리자 권한으로 회원 목록을 조회합니다.")
    @SecurityRequirement(name = "bearerAuth")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공"),
            @ApiResponse(responseCode = "401", description = "인증 필요"),
            @ApiResponse(responseCode = "403", description = "권한 없음")
    })
    @GetMapping("/members")
    public List<MemberManagementResponse> getMemberList(
            @Parameter(description = "검색 키워드", example = "kim")
            @RequestParam(required = false) String keyword,
            @Parameter(description = "조회 개수", example = "10")
            @RequestParam(defaultValue = "10") int limit,
            @Parameter(description = "조회 시작 위치", example = "0")
            @RequestParam(defaultValue = "0") int offset
    ) {
        return memberManagementService.getMemberList(keyword, limit, offset);
    }
}
