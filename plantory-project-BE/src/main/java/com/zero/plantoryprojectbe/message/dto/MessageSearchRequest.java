package com.zero.plantoryprojectbe.message.dto;

import com.zero.plantoryprojectbe.global.plantoryEnum.BoxType;
import com.zero.plantoryprojectbe.global.plantoryEnum.TargetType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "쪽지 검색 조건")
public class MessageSearchRequest {

    @Schema(description = "회원 ID", example = "8")
    private Long memberId;

    @Schema(description = "쪽지함 타입", example = "RECEIVED")
    private BoxType boxType;

    @Schema(description = "연결 대상 타입", example = "SHARING")
    private TargetType targetType;

    @Schema(description = "제목 검색어", example = "문의")
    private String title;

    @Schema(description = "조회 시작 위치", example = "0")
    private int offset;

    @Schema(description = "조회 개수", example = "10")
    private int limit;
}
