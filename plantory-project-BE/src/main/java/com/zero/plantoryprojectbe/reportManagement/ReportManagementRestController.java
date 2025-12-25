package com.zero.plantoryprojectbe.reportManagement;

import com.zero.plantoryprojectbe.image.dto.ImageDTO;
import com.zero.plantoryprojectbe.global.plantoryEnum.ImageTargetType;
import com.zero.plantoryprojectbe.image.service.ImageService;
import com.zero.plantoryprojectbe.reportManagement.dto.*;
import com.zero.plantoryprojectbe.reportManagement.service.ReportManagementService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/reportManagement")
@Tag(name = "ReportManagement", description = "신고 관리 API")
public class ReportManagementRestController {

    private final ReportManagementService reportManagementService;
    private final ImageService imageService;

    @Operation(
            summary = "신고 목록 조회",
            description = "신고 목록을 조건 검색 + 페이징으로 조회합니다."
    )
    @GetMapping("/list")
    public ResponseEntity<ReportManagementPageResponse> getReportList(
            @Parameter(description = "검색 조건", example = "page/size/status/keyword 등")
            ReportManagementSearchRequest request
    ) {
        return ResponseEntity.ok().body(reportManagementService.getReporManagmentList(request));
    }

    @Operation(
            summary = "신고 목록 소프트 삭제",
            description = "ids에 해당하는 신고를 소프트 삭제합니다. 대상 없으면 400을 반환합니다."
    )
    @PutMapping("/softDelete")
    public ResponseEntity<?> softDelete(
            @RequestBody IdListRequest request
    ) {
        int updated = reportManagementService.deleteReporManagmentList(request.getIds());

        if (updated > 0) {
            return ResponseEntity.ok("삭제 성공: " + updated + "건");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("삭제 실패: 삭제 대상 없음");
        }
    }

    @Operation(
            summary = "신고 상세 조회",
            description = "id로 신고 상세 정보를 조회합니다."
    )
    @GetMapping("/detail/{id}")
    public ResponseEntity<ReportManagementDetailResponse> getReportDetail(
            @Parameter(description = "신고 ID", example = "1")
            @PathVariable("id") Long id
    ) {
        return ResponseEntity.ok().body(reportManagementService.getReportDetail(id));
    }

    @Operation(
            summary = "신고 첨부 이미지 조회",
            description = "targetType/targetId로 첨부 이미지 목록을 조회합니다."
    )
    @GetMapping("/images")
    public List<ImageDTO> getImagesByTarget(
            @Parameter(description = "타겟 타입", example = "REPORT")
            @RequestParam("targetType") ImageTargetType targetType,
            @Parameter(description = "타겟 ID", example = "1")
            @RequestParam("targetId") Long targetId
    ) {
        return imageService.getImagesByTarget(targetType, targetId);
    }

    @Operation(
            summary = "신고 처리",
            description = "신고를 처리(제재/메모 반영)합니다."
    )
    @PostMapping("/{reportId}")
    public ResponseEntity<?> processReport(
            @Parameter(description = "신고 ID", example = "1")
            @PathVariable Long reportId,
            @RequestBody ReportProcessRequest request
    ) {
        reportManagementService.processReport(
                reportId,
                request.getTargetMemberId(),
                request.getAdminMemo(),
                request.getStopDays()
        );
        return ResponseEntity.ok("처리 완료");
    }
}
