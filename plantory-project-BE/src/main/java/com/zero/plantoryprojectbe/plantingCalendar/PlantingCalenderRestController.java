package com.zero.plantoryprojectbe.plantingCalendar;

import com.zero.plantoryprojectbe.image.dto.ImageDTO;
import com.zero.plantoryprojectbe.global.security.MemberDetail;
import com.zero.plantoryprojectbe.plantingCalendar.dto.DiaryRequest;
import com.zero.plantoryprojectbe.plantingCalendar.dto.DiaryResponse;
import com.zero.plantoryprojectbe.plantingCalendar.dto.MyPlantDiaryResponse;
import com.zero.plantoryprojectbe.plantingCalendar.dto.PlantingCalendarResponse;
import com.zero.plantoryprojectbe.plantingCalendar.service.PlantingCalenderService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/api/plantingCalender")
@RequiredArgsConstructor
@Tag(name = "PlantingCalendar", description = "캘린더(물주기/일지) API")
public class PlantingCalenderRestController {

    private final PlantingCalenderService plantingCalenderService;

    @Operation(summary = "일지 캘린더 조회", description = "기간 내 일지 일정을 캘린더 형태로 조회합니다.")
    @SecurityRequirement(name = "bearerAuth")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공"),
            @ApiResponse(responseCode = "401", description = "인증 필요"),
            @ApiResponse(responseCode = "403", description = "권한 없음")
    })
    @GetMapping("/diary")
    public List<PlantingCalendarResponse> getPlantingDiaryCalendar(
            @AuthenticationPrincipal MemberDetail memberDetail,
            @Parameter(description = "조회 시작일", example = "2025-12-01T00:00:00")
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @Parameter(description = "조회 종료일", example = "2025-12-31T23:59:59")
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate
    ) {
        Long memberId = memberDetail.memberResponse().getMemberId();
        return plantingCalenderService.getDiaryCalendar(memberId, startDate, endDate);
    }

    @Operation(summary = "물주기 캘린더 조회", description = "기간 내 물주기 일정을 캘린더 형태로 조회합니다.")
    @SecurityRequirement(name = "bearerAuth")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공"),
            @ApiResponse(responseCode = "401", description = "인증 필요"),
            @ApiResponse(responseCode = "403", description = "권한 없음")
    })
    @GetMapping("/watering")
    public List<PlantingCalendarResponse> getPlantingWateringCalendar(
            @AuthenticationPrincipal MemberDetail memberDetail,
            @Parameter(description = "조회 시작일", example = "2025-12-01T00:00:00")
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @Parameter(description = "조회 종료일", example = "2025-12-31T23:59:59")
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate
    ) {
        Long memberId = memberDetail.memberResponse().getMemberId();
        return plantingCalenderService.getWateringCalendar(memberId, startDate, endDate);
    }

    @Operation(summary = "물주기 체크 처리", description = "물주기 완료 체크를 처리합니다.")
    @SecurityRequirement(name = "bearerAuth")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "처리 성공"),
            @ApiResponse(responseCode = "400", description = "처리 실패"),
            @ApiResponse(responseCode = "401", description = "인증 필요"),
            @ApiResponse(responseCode = "403", description = "권한 없음")
    })
    @PutMapping("/watering")
    public ResponseEntity<Map<String, String>> updateWateringFlag(
            @Parameter(description = "물주기 ID", example = "10")
            @RequestParam Long wateringId
    ) {
        int result = plantingCalenderService.updatePlantWateringCheck(wateringId);
        if (result == 0) {
            return ResponseEntity.status(400).body(Map.of("message", "check fail"));
        }
        return ResponseEntity.status(200).body(Map.of("message", "check success"));
    }

    @Operation(summary = "물주기 일정 삭제", description = "내 식물 ID 기준으로 물주기 일정을 삭제합니다.")
    @SecurityRequirement(name = "bearerAuth")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "삭제 성공"),
            @ApiResponse(responseCode = "400", description = "삭제 실패"),
            @ApiResponse(responseCode = "401", description = "인증 필요"),
            @ApiResponse(responseCode = "403", description = "권한 없음")
    })
    @DeleteMapping("/watering")
    public ResponseEntity<Map<String, String>> deleteWatering(
            @Parameter(description = "내 식물 ID", example = "5")
            @RequestParam Long myplantId,
            @AuthenticationPrincipal MemberDetail memberDetail
    ) {
        Long memberId = memberDetail.memberResponse().getMemberId();
        int result = plantingCalenderService.removePlantWatering(myplantId, memberId);
        if (result == 1) {
            return ResponseEntity.status(200).body(Map.of("message", "watering delete success"));
        }
        return ResponseEntity.status(400).body(Map.of("message", "watering check fail"));
    }

    @Operation(summary = "일지 수정 모달 정보 조회", description = "일지 수정 화면에 필요한 일지 정보와 이미지 목록을 조회합니다.")
    @SecurityRequirement(name = "bearerAuth")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공"),
            @ApiResponse(responseCode = "400", description = "일지 없음"),
            @ApiResponse(responseCode = "401", description = "인증 필요"),
            @ApiResponse(responseCode = "403", description = "권한 없음")
    })
    @GetMapping("/diaryInfo/{diaryId}")
    public ResponseEntity<?> getDiaryUpdateModalInfo(
            @Parameter(description = "일지 ID", example = "21")
            @PathVariable Long diaryId
    ) {
        DiaryResponse diary = plantingCalenderService.findDiaryUpdateInfo(diaryId);
        if (diary == null) {
            return ResponseEntity.status(400).body(Map.of("message", "diary not found", "diaryId", diaryId));
        }
        List<ImageDTO> images = plantingCalenderService.findDiaryUpdateImageInfo(diaryId);
        return ResponseEntity.ok(Map.of("diary", diary, "images", images));
    }

    @Operation(summary = "일지 삭제", description = "일지 ID로 일지를 삭제합니다.")
    @SecurityRequirement(name = "bearerAuth")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "삭제 성공"),
            @ApiResponse(responseCode = "400", description = "삭제 실패"),
            @ApiResponse(responseCode = "401", description = "인증 필요"),
            @ApiResponse(responseCode = "403", description = "권한 없음")
    })
    @DeleteMapping("/diary/{diaryId}")
    public ResponseEntity<Map<String, String>> deleteDiary(
            @Parameter(description = "일지 ID", example = "21")
            @PathVariable Long diaryId
    ) {
        if (plantingCalenderService.removeDiary(diaryId) == 0)
            return ResponseEntity.status(400).body(Map.of("message", "diary delete fail"));
        return ResponseEntity.status(200).body(Map.of("message", "diary delete success"));
    }

    @Operation(summary = "일지 등록", description = "일지를 등록합니다. files는 선택이며 여러 파일을 업로드할 수 있습니다.")
    @SecurityRequirement(name = "bearerAuth")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "등록 성공"),
            @ApiResponse(responseCode = "400", description = "등록 실패"),
            @ApiResponse(responseCode = "401", description = "인증 필요"),
            @ApiResponse(responseCode = "403", description = "권한 없음")
    })
    @PostMapping("/diary")
    public ResponseEntity<Map<String, String>> createDiary(
            @Parameter(description = "일지 등록 정보")
            @ModelAttribute DiaryRequest request,
            @Parameter(description = "업로드 파일 목록", required = false)
            @RequestPart(value = "files", required = false) List<MultipartFile> files,
            @AuthenticationPrincipal MemberDetail memberDetail
    ) throws IOException {
        Long memberId = memberDetail.memberResponse().getMemberId();
        if (plantingCalenderService.registerDiary(request, files, memberId) == 0)
            return ResponseEntity.status(400).body(Map.of("message", "diary register fail"));
        return ResponseEntity.status(200).body(Map.of("message", "diary create success"));
    }

    @Operation(summary = "일지 작성용 내 식물 목록 조회", description = "일지 작성 화면에서 선택할 내 식물 목록을 조회합니다.")
    @SecurityRequirement(name = "bearerAuth")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공"),
            @ApiResponse(responseCode = "401", description = "인증 필요"),
            @ApiResponse(responseCode = "403", description = "권한 없음")
    })
    @GetMapping("/diary/myplant")
    public List<MyPlantDiaryResponse> getMyPlant(
            @AuthenticationPrincipal MemberDetail memberDetail
    ) {
        Long memberId = memberDetail.memberResponse().getMemberId();
        return plantingCalenderService.getMyPlant(memberId);
    }
}
