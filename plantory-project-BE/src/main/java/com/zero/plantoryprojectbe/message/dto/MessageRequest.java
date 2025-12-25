package com.zero.plantoryprojectbe.message.dto;

import com.zero.plantoryprojectbe.global.plantoryEnum.MessageTargetType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "쪽지 전송 요청")
public class MessageRequest {

    @Schema(description = "쪽지 ID", example = "1")
    private Long messageId;

    @Schema(description = "보낸 회원 ID", example = "8")
    private Long senderId;

    @Schema(description = "받는 회원 ID", example = "12")
    private Long receiverId;

    @Schema(description = "쪽지 제목", example = "문의드립니다")
    private String title;

    @Schema(description = "쪽지 내용", example = "나눔 가능할까요?")
    private String content;

    @Schema(description = "연결 대상 타입", example = "SHARING")
    private MessageTargetType targetType;

    @Schema(description = "연결 대상 ID", example = "15")
    private Long targetId;

    @Schema(description = "생성 일시", example = "2025-01-10T10:30:00")
    private Date createdAt;

    @Schema(description = "읽음 일시", example = "2025-01-10T11:00:00")
    private Date readFlag;

    @Schema(description = "삭제 일시", example = "2025-01-11T09:00:00")
    private Date delFlag;
}
