package com.zero.plantoryprojectbe.report;

import com.zero.plantoryprojectbe.global.security.MemberDetail;
import com.zero.plantoryprojectbe.report.dto.NameListResponse;
import com.zero.plantoryprojectbe.report.dto.ReportRequest;
import com.zero.plantoryprojectbe.report.service.ReportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Tag(name = "Report", description = "신고 API")
@RestController
@RequestMapping("/api/report")
@RequiredArgsConstructor
public class ReportRestController {

    private final ReportService reportService;

    @Operation(
            summary = "닉네임으로 사용자 검색",
            description = "신고 대상 선택을 위해 닉네임 키워드로 사용자를 검색합니다. (인증 필요)"
    )
    @GetMapping("/users")
    public ResponseEntity<List<NameListResponse>> searchUserByNickname(
            @Parameter(description = "검색 닉네임", example = "식물집사")
            @RequestParam String nickname,
            @Parameter(hidden = true)
            @AuthenticationPrincipal MemberDetail memberDetail
    ) {
        Long viewerId = memberDetail.memberResponse().getMemberId();
        return ResponseEntity.ok().body(reportService.getUsersIdByNickname(nickname, viewerId));
    }

    @Operation(
            summary = "신고 등록",
            description = "신고를 등록합니다. files는 선택이며, 실패 시 400을 반환합니다. (인증 필요)"
    )
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Map<String, String>> registerReport(
            @Parameter(description = "신고 등록 폼데이터", example = "reportedId/reason/content 등")
            @ModelAttribute ReportRequest request,
            @Parameter(description = "첨부 이미지(선택)", example = "files")
            @RequestParam(value = "files", required = false) List<MultipartFile> files,
            @Parameter(hidden = true)
            @AuthenticationPrincipal MemberDetail memberDetail
    ) throws IOException {

        Long repoterId = memberDetail.memberResponse().getMemberId();
        request.setReporterId(repoterId);

        int saved = reportService.registerReport(request, files);
        int expected = (files == null ? 0 : files.size()) + 1;

        if (saved == expected) {
            return ResponseEntity.ok(Map.of("message", "registry report success"));
        }
        return ResponseEntity.badRequest().body(Map.of("message", "register report fail"));
    }
}
