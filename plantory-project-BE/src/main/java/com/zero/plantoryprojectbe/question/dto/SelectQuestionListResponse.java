package com.zero.plantoryprojectbe.question.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "질문 목록 조회 응답 DTO")
public class SelectQuestionListResponse {

    @Schema(description = "질문 ID", example = "100")
    private Long questionId;

    @Schema(description = "질문 제목", example = "잎이 노랗게 변해요")
    private String title;

    @Schema(description = "작성자 닉네임", example = "식물집사")
    private String nickname;

    @Schema(description = "생성일시", example = "2024-06-01 10:00")
    private String createdAt;

    @Schema(description = "수정일시", example = "2024-06-02 09:00")
    private String updatedAt;

    @Schema(description = "답변 수", example = "3")
    private Integer answerCount;

    @Schema(description = "대표 이미지 URL", example = "https://cdn.example.com/thumb.jpg")
    private String imageUrl;

    @Schema(description = "작성자 회원 ID", example = "10")
    private Long memberId;
}
