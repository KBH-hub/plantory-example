package com.zero.plantoryprojectbe.question.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "질문 등록/수정 요청 DTO")
public class QuestionRequest {

    @Schema(description = "질문 ID (수정 시)", example = "100")
    private Long questionId;

    @Schema(description = "작성자 회원 ID (서버에서 세팅)", example = "10")
    private Long memberId;

    @Schema(description = "질문 제목", example = "잎이 노랗게 변해요")
    private String title;

    @Schema(description = "질문 내용", example = "최근 물을 자주 줬는데 잎이 노랗습니다.")
    private String content;

    @Schema(description = "생성일시 (서버에서 세팅)", example = "2024-06-01T10:00:00")
    private LocalDateTime createdAt;

    @Schema(description = "수정일시 (서버에서 세팅)", example = "2024-06-02T09:00:00")
    private LocalDateTime updatedAt;

    @Schema(description = "삭제 여부 플래그 (서버에서 세팅)", example = "null")
    private LocalDateTime delFlag;

    @Schema(description = "삭제할 이미지 ID(JSON 배열 문자열)", example = "[1,2,3]")
    private String deletedImageIds;

    @Schema(description = "삭제 이미지 ID 리스트 (서버에서 파싱)", example = "[1,2,3]")
    private List<Long> deletedImageIdList;
}
