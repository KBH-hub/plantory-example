package com.zero.plantoryprojectbe.report.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "닉네임 검색 결과 응답 DTO")
public class NameListResponse {

    @Schema(description = "회원 ID", example = "10")
    private Long memberId;

    @Schema(description = "닉네임", example = "식물집사")
    private String nickname;
}
