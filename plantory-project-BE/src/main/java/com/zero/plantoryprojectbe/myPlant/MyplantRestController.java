package com.zero.plantoryprojectbe.myPlant;

import com.zero.plantoryprojectbe.global.security.MemberDetail;
import com.zero.plantoryprojectbe.myPlant.dto.MyPlantRequest;
import com.zero.plantoryprojectbe.myPlant.dto.MyPlantResponse;
import com.zero.plantoryprojectbe.myPlant.service.MyPlantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/api/myPlant")
@RequiredArgsConstructor
@Tag(name = "MyPlant", description = "내 식물 관리 API")
public class MyplantRestController {

    private final MyPlantService myPlantService;

    @Operation(summary = "내 식물 목록 조회", description = "로그인한 사용자의 내 식물 목록을 조회합니다.")
    @SecurityRequirement(name = "bearerAuth")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공"),
            @ApiResponse(responseCode = "401", description = "인증 필요"),
            @ApiResponse(responseCode = "403", description = "권한 없음")
    })
    @GetMapping("/list")
    public List<MyPlantResponse> getMyPlantList(
            @AuthenticationPrincipal MemberDetail memberDetail,
            @Parameter(description = "식물 이름 검색", example = "몬스테라")
            @RequestParam String name,
            @Parameter(description = "조회 개수", example = "10")
            @RequestParam Integer limit,
            @Parameter(description = "조회 시작 위치", example = "0")
            @RequestParam Integer offset
    ) {
        Long memberId = memberDetail.memberResponse().getMemberId();
        return myPlantService.getMyPlantList(memberId, name, limit, offset);
    }

    @Operation(summary = "내 식물 등록", description = "내 식물을 등록합니다. 파일은 선택입니다.")
    @SecurityRequirement(name = "bearerAuth")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "등록 성공"),
            @ApiResponse(responseCode = "400", description = "등록 실패"),
            @ApiResponse(responseCode = "401", description = "인증 필요"),
            @ApiResponse(responseCode = "403", description = "권한 없음")
    })
    @PostMapping
    public ResponseEntity<Map<String, String>> addMyPlant(
            @Parameter(description = "내 식물 등록 정보")
            @ModelAttribute MyPlantRequest request,
            @Parameter(description = "업로드 파일", required = false)
            @RequestParam(value = "file", required = false) MultipartFile file,
            @AuthenticationPrincipal MemberDetail memberDetail
    ) throws IOException {
        Long memberId = memberDetail.memberResponse().getMemberId();
        request.setMemberId(memberId);
        if (myPlantService.registerMyPlant(request, file, memberId) == 0)
            return ResponseEntity.status(400).body(Map.of("message", "myPlant regist fail"));
        return ResponseEntity.ok().body(Map.of("message", "myPlant regist success"));
    }

    @Operation(summary = "내 식물 수정", description = "내 식물 정보를 수정합니다. delFile은 삭제할 이미지 ID입니다.")
    @SecurityRequirement(name = "bearerAuth")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "수정 성공"),
            @ApiResponse(responseCode = "400", description = "수정 실패"),
            @ApiResponse(responseCode = "401", description = "인증 필요"),
            @ApiResponse(responseCode = "403", description = "권한 없음")
    })
    @PutMapping
    public ResponseEntity<Map<String, String>> updateMyPlant(
            @Parameter(description = "내 식물 수정 정보")
            @ModelAttribute MyPlantRequest request,
            @Parameter(description = "삭제할 이미지 ID", required = false, example = "100")
            @RequestParam(name = "delFile", required = false) Long delFile,
            @Parameter(description = "업로드 파일", required = false)
            @RequestParam(name = "file", required = false) MultipartFile file,
            @AuthenticationPrincipal MemberDetail memberDetail
    ) throws IOException {
        Long memberId = memberDetail.memberResponse().getMemberId();
        request.setMemberId(memberId);
        if (myPlantService.updateMyPlant(request, delFile, file, memberId) == 0)
            return ResponseEntity.status(400).body(Map.of("message", "myPlant regist fail"));
        return ResponseEntity.ok().body(Map.of("message", "myPlant regist success"));
    }

    @Operation(summary = "내 식물 삭제", description = "내 식물을 삭제합니다. delFile은 삭제할 이미지 ID입니다.")
    @SecurityRequirement(name = "bearerAuth")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "삭제 성공"),
            @ApiResponse(responseCode = "400", description = "삭제 실패"),
            @ApiResponse(responseCode = "401", description = "인증 필요"),
            @ApiResponse(responseCode = "403", description = "권한 없음")
    })
    @DeleteMapping
    public ResponseEntity<Map<String, String>> removeMyPlant(
            @Parameter(description = "내 식물 ID", example = "5")
            @RequestParam Long myplantId,
            @Parameter(description = "삭제할 이미지 ID", required = false, example = "100")
            @RequestParam(required = false) Long delFile
    ) throws IOException {
        if (myPlantService.removePlant(myplantId, delFile) == 0)
            return ResponseEntity.status(400).body(Map.of("message", "myPlant regist fail"));
        return ResponseEntity.ok().body(Map.of("message", "myPlant regist success"));
    }
}
