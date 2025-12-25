package com.zero.plantoryprojectbe.dictionary;

import com.fasterxml.jackson.databind.JsonNode;
import com.zero.plantoryprojectbe.dictionary.service.DryGardenApiService;
import com.zero.plantoryprojectbe.dictionary.service.GardenApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/api/dictionary")
@RequiredArgsConstructor
@Tag(name = "Dictionary", description = "식물 도감 조회 API")
public class DictionaryRestController {

    private final GardenApiService gardenApiService;
    private final DryGardenApiService dryGardenApiService;

    @Operation(summary = "일반 식물 도감 목록 조회", description = "일반 식물 도감 목록을 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공")
    })
    @GetMapping("/garden")
    public JsonNode getGarden(
            @Parameter(description = "페이지 번호", example = "1")
            @RequestParam(defaultValue = "1") String pageNo,
            @Parameter(description = "페이지당 조회 개수", example = "10")
            @RequestParam(defaultValue = "10") String numOfRows,
            @Parameter(description = "광도 코드", example = "01")
            @RequestParam(required = false) String lightCode
    ) {
        return gardenApiService.getGardenList(pageNo, numOfRows, lightCode);
    }

    @Operation(summary = "일반 식물 도감 상세 조회", description = "식물 컨텐츠 번호로 상세 정보를 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공"),
            @ApiResponse(responseCode = "404", description = "식물 정보 없음")
    })
    @GetMapping("/garden/{cntntsNo}")
    public JsonNode getGardenDetail(
            @Parameter(description = "식물 컨텐츠 번호", example = "12345")
            @PathVariable String cntntsNo
    ) {
        return gardenApiService.getGardenDetail(cntntsNo);
    }

    @Operation(summary = "건조 식물 도감 목록 조회", description = "건조 식물 도감 목록을 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공")
    })
    @GetMapping("/dry")
    public JsonNode getDryGarden(
            @Parameter(description = "페이지 번호", example = "1")
            @RequestParam(defaultValue = "1") String pageNo,
            @Parameter(description = "페이지당 조회 개수", example = "10")
            @RequestParam(defaultValue = "10") String numOfRows,
            @Parameter(description = "분류 코드", example = "A01")
            @RequestParam(defaultValue = "") String sClCode
    ) {
        return dryGardenApiService.getDryGardenList(pageNo, numOfRows, sClCode);
    }

    @Operation(summary = "건조 식물 도감 상세 조회", description = "건조 식물 컨텐츠 번호로 상세 정보를 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공"),
            @ApiResponse(responseCode = "404", description = "식물 정보 없음")
    })
    @GetMapping("/dry/{cntntsNo}")
    public JsonNode getDryGardenDetail(
            @Parameter(description = "식물 컨텐츠 번호", example = "67890")
            @PathVariable String cntntsNo
    ) {
        return dryGardenApiService.getDryGardenDetail(cntntsNo);
    }
}
