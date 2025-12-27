package com.zero.plantoryprojectbe.weightManagement;

import com.zero.plantoryprojectbe.global.security.MemberPrincipal;
import com.zero.plantoryprojectbe.weightManagement.dto.*;
import com.zero.plantoryprojectbe.weightManagement.service.WeightManagementService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "WeightManagement", description = "식물 무게/관리 지표 API")
@RestController
@RequestMapping("/api/weightManagement")
@RequiredArgsConstructor
public class WeightManagementRestController {

    private final WeightManagementService weightManagementService;

    @Operation(
            summary = "회원 식물 무게 목록 조회",
            description = "무게 기록 목록을 조회합니다. keyword/range는 선택이며 limit/offset으로 페이징합니다."
    )
    @GetMapping("/list")
    public List<WeightManagementResponse> getMemberWeightList(
            @Parameter(description = "검색 키워드(선택)", example = "몬스테라")
            @RequestParam(required = false) String keyword,
            @Parameter(description = "조회 개수", example = "10")
            @RequestParam(defaultValue = "10") int limit,
            @Parameter(description = "오프셋", example = "0")
            @RequestParam(defaultValue = "0") int offset,
            @Parameter(description = "기간 범위(선택)", example = "7d")
            @RequestParam(required = false) String range
    ) {
        return weightManagementService.getMemberWeightList(keyword, limit, offset, range);
    }

    @Operation(
            summary = "무게 기록 저장",
            description = "무게 기록을 저장합니다. memberId는 토큰에서 추출합니다. (인증 필요)"
    )
    @PostMapping("/list")
    public void saveWeights(
            @Parameter(hidden = true)
            @AuthenticationPrincipal MemberPrincipal principal,
            @RequestBody SaveWeightRequest saveWeightRequest
    ) {
        Long memberId = principal.getMemberId();
        weightManagementService.saveWeights(memberId, saveWeightRequest);
    }

    @Operation(
            summary = "최신 무게 기록 조회",
            description = "최신 무게 기록을 조회합니다."
    )
    @GetMapping("/latest")
    public WeightLoggingResponse getLatestWeights() {
        return weightManagementService.getLatestWeights();
    }

    @Operation(
            summary = "관리 필요 식물 카운트 조회",
            description = "관리(관수 등) 주의가 필요한 식물 카운트를 조회합니다."
    )
    @GetMapping("/careCounts")
    public ResponseEntity<?> getCareCounts() {
        return ResponseEntity.ok(weightManagementService.getPlantsNeedingAttention());
    }

    @Operation(
            summary = "관리 지표(비율) 조회",
            description = "관리 지표(비율) 정보를 조회합니다."
    )
    @GetMapping("/rate")
    public ResponseEntity<RateResponse> getRate() {
        return ResponseEntity.ok(weightManagementService.getRate());
    }

    @Operation(
            summary = "관리 지표(비율) 저장",
            description = "관리 지표(비율) 정보를 저장합니다. memberId는 토큰에서 추출합니다. (인증 필요)"
    )
    @PostMapping("/rate")
    public void saveRate(
            @Parameter(hidden = true)
            @AuthenticationPrincipal MemberPrincipal principal,
            @RequestBody SaveRateRequest saveRateRequest
    ) {
        Long memberId = principal.getMemberId();
        weightManagementService.saveRate(memberId, saveRateRequest);
    }
}
