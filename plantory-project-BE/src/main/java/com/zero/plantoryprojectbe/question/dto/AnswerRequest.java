package com.zero.plantoryprojectbe.question.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "답변 등록/수정/삭제 요청 DTO")
public class AnswerRequest {

    @Schema(description = "답변 ID (수정/삭제 시)", example = "200")
    private Long answerId;

    @Schema(description = "질문 ID (등록 시)", example = "100")
    private Long questionId;

    @Schema(description = "작성자 회원 ID (서버에서 세팅)", example = "10")
    private Long writerId;

    @Schema(description = "답변 내용", example = "과습일 수 있으니 배수를 확인하세요.")
    private String content;

    @Schema(description = "생성일시 (서버에서 세팅)", example = "2024-06-01T10:15:30")
    private LocalDateTime createdAt;

    @Schema(description = "수정일시 (서버에서 세팅)", example = "2024-06-02T09:10:00")
    private LocalDateTime updatedAt;

    @Schema(description = "삭제 여부 플래그 (서버에서 세팅)", example = "null")
    private LocalDateTime delFlag;
}
