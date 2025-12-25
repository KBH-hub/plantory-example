package com.zero.plantoryprojectbe.question.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "답변 목록 조회 응답 DTO")
public class SelectAnswerListResponse {

    @Schema(description = "답변 ID", example = "200")
    private Long answerId;

    @Schema(description = "작성자 회원 ID", example = "10")
    private Long writerId;

    @Schema(description = "작성자 닉네임", example = "식물집사")
    private String nickname;

    @Schema(description = "답변 내용", example = "과습일 가능성이 높아요.")
    private String content;

    @Schema(description = "생성일시", example = "2024-06-01 10:15")
    private String createdAt;

    @Schema(description = "수정일시", example = "2024-06-02 09:10")
    private String updatedAt;
}
