package com.zero.plantoryprojectbe.weightManagement.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "회원별 가중치/관리 상태 응답 DTO")
public class WeightManagementResponse {

    @Schema(description = "회원 ID", example = "10")
    private Long memberId;

    @Schema(description = "회원 이름", example = "홍길동")
    private String membername;

    @Schema(description = "닉네임", example = "식물집사")
    private String nickname;

    @Schema(description = "검색 가중치", example = "5")
    private Integer searchWeight;

    @Schema(description = "질문 가중치", example = "3")
    private Integer questionWeight;

    @Schema(description = "관리 필요 식물 수", example = "2")
    private Integer plantsNeedingAttention;

    @Schema(description = "전체 회원 수", example = "125")
    private Long totalCount;
}
