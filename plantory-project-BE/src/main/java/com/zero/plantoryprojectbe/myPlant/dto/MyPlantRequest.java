package com.zero.plantoryprojectbe.myPlant.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "내 식물 등록/수정 요청")
public class MyPlantRequest {

    @Schema(description = "내 식물 ID", example = "5")
    private Long myplantId;

    @Schema(description = "회원 ID", example = "8")
    private Long memberId;

    @Schema(description = "식물 이름", example = "몬스테라")
    private String name;

    @Schema(description = "식물 종류", example = "관엽식물")
    private String type;

    @Schema(description = "키우기 시작한 날짜", example = "2025-01-01T00:00:00")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime startAt;

    @Schema(description = "종료 예정 날짜", example = "2025-12-31T00:00:00")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime endDate;

    @Schema(description = "물 주기 간격(일)", example = "3")
    private int interval;

    @Schema(description = "토양 종류", example = "배양토")
    private String soil;

    @Schema(description = "적정 온도", example = "20~25℃")
    private String temperature;

    @Schema(description = "생성 일시", example = "2025-01-01T10:00:00")
    private Date createdAt;

    @Schema(description = "삭제 일시", example = "2025-12-31T10:00:00")
    private Date delFlag;
}
