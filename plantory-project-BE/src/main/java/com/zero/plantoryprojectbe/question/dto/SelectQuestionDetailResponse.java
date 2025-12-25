package com.zero.plantoryprojectbe.question.dto;

import com.zero.plantoryprojectbe.image.dto.ImageDTO;
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
@Schema(description = "질문 상세 조회 응답 DTO")
public class SelectQuestionDetailResponse {

    @Schema(description = "질문 ID", example = "100")
    private Long questionId;

    @Schema(description = "작성자 회원 ID", example = "10")
    private Long memberId;

    @Schema(description = "작성자 닉네임", example = "식물집사")
    private String nickname;

    @Schema(description = "질문 제목", example = "잎이 노랗게 변해요")
    private String title;

    @Schema(description = "질문 내용", example = "물을 자주 줬는데 상태가 안 좋아요.")
    private String content;

    @Schema(description = "생성일시", example = "2024-06-01 10:00")
    private String createdAt;

    @Schema(description = "수정일시", example = "2024-06-02 09:00")
    private String updatedAt;

    @Schema(description = "대표 이미지 URL", example = "https://cdn.example.com/image.jpg")
    private String imageUrl;

    @Schema(description = "이미지 목록")
    private List<ImageDTO> images;
}
