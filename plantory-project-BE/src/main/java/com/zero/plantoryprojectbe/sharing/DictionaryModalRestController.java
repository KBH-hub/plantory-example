package com.zero.plantoryprojectbe.sharing;

import com.fasterxml.jackson.databind.JsonNode;
import com.zero.plantoryprojectbe.dictionary.service.DryGardenApiService;
import com.zero.plantoryprojectbe.dictionary.service.GardenApiService;
import com.zero.plantoryprojectbe.sharing.dto.DictionaryModalSearchRequest;
import com.zero.plantoryprojectbe.sharing.dto.DictionaryModalSearchlResponse;
import com.zero.plantoryprojectbe.sharing.service.DictionaryModalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "DictionaryModal", description = "식물사전 모달 조회 API")
@RestController
@RequestMapping("/api/dictionaryModal")
@RequiredArgsConstructor
public class DictionaryModalRestController {

    private final GardenApiService gardenApiService;
    private final DryGardenApiService dryGardenApiService;
    private final DictionaryModalService dictionaryModalService;

    @Operation(
            summary = "식물 검색",
            description = "검색어(word)로 식물 목록을 조회합니다."
    )
    @GetMapping("/search")
    public List<DictionaryModalSearchRequest> searchPlants(
            @Parameter(description = "검색어", example = "몬스테라")
            @RequestParam String word
    ) {
        return dictionaryModalService.search(word);
    }

    @Operation(
            summary = "일반 식물 상세 조회",
            description = "공공데이터(정원) 상세를 cntntsNo로 조회 후 모달용 응답으로 변환합니다. 외부 API 실패 시 에러가 발생할 수 있습니다."
    )
    @GetMapping("/garden/{cntntsNo}")
    public DictionaryModalSearchlResponse getGardenDetail(
            @Parameter(description = "콘텐츠 번호", example = "12345")
            @PathVariable String cntntsNo
    ) {
        JsonNode node = gardenApiService.getGardenDetail(cntntsNo);
        return dictionaryModalService.convertGardenDetail(node);
    }

    @Operation(
            summary = "건조 식물 상세 조회",
            description = "공공데이터(건조정원) 상세를 cntntsNo로 조회 후 모달용 응답으로 변환합니다. 외부 API 실패 시 에러가 발생할 수 있습니다."
    )
    @GetMapping("/dry/{cntntsNo}")
    public DictionaryModalSearchlResponse getDryDetail(
            @Parameter(description = "콘텐츠 번호", example = "67890")
            @PathVariable String cntntsNo
    ) {
        JsonNode node = dryGardenApiService.getDryGardenDetail(cntntsNo);
        return dictionaryModalService.convertDryDetail(node);
    }
}
