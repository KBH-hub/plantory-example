package com.zero.plantoryprojectbe.image.dto;

import com.zero.plantoryprojectbe.global.plantoryEnum.ImageTargetType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "이미지 정보")
public class ImageDTO {

    @Schema(description = "이미지 ID", example = "100")
    private Long imageId;

    @Schema(description = "업로드한 회원 ID", example = "8")
    private Long memberId;

    @Schema(description = "이미지 대상 타입", example = "SHARING")
    private ImageTargetType targetType;

    @Schema(description = "대상 ID", example = "15")
    private Long targetId;

    @Schema(description = "이미지 접근 URL", example = "https://example.com/image.jpg")
    private String fileUrl;

    @Schema(description = "원본 파일명", example = "plant.jpg")
    private String fileName;

    @Schema(description = "업로드 일시", example = "2025-12-23T12:30:00")
    private LocalDateTime createdAt;

    @Schema(description = "삭제 일시(삭제되지 않은 경우 null)", example = "2025-12-30T10:00:00")
    private LocalDateTime delFlag;
}