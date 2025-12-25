package com.zero.plantoryprojectbe.question.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "질문 목록 페이징 응답 DTO")
public class QuestionListPageResponse {

    @Schema(description = "질문 목록")
    private List<SelectQuestionListResponse> list;

    @Schema(description = "전체 질문 수", example = "125")
    private int totalCount;

    @Schema(description = "현재 페이지", example = "1")
    private int page;

    @Schema(description = "페이지 사이즈", example = "10")
    private int size;
}
