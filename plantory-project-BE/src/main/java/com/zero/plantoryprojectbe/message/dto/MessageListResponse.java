package com.zero.plantoryprojectbe.message.dto;

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
@Schema(description = "쪽지 목록 응답")
public class MessageListResponse {

    @Schema(description = "쪽지 ID", example = "1")
    private Long messageId;

    @Schema(description = "보낸 회원 ID", example = "8")
    private Long senderId;

    @Schema(description = "보낸 회원 닉네임", example = "앨리스")
    private String senderNickname;

    @Schema(description = "받는 회원 ID", example = "12")
    private Long receiverId;

    @Schema(description = "받는 회원 닉네임", example = "초록이")
    private String receiverNickname;

    @Schema(description = "쪽지 제목", example = "문의드립니다")
    private String title;

    @Schema(description = "쪽지 내용", example = "나눔 가능할까요?")
    private String content;

    @Schema(description = "연결 대상 타입", example = "SHARING")
    private String targetType;

    @Schema(description = "연결 대상 제목", example = "몬스테라 나눔")
    private String targetTitle;

    @Schema(description = "생성 일시", example = "2025-01-10T10:30:00")
    private Date createdAt;

    @Schema(description = "읽음 일시", example = "2025-01-10T11:00:00")
    private Date readFlag;

    @Schema(description = "수신자 삭제 일시", example = "2025-01-11T09:00:00")
    private Date delFlag;

    @Schema(description = "발신자 삭제 일시", example = "2025-01-11T09:00:00")
    private Date senderDelFlag;

    @Schema(description = "전체 쪽지 수", example = "42")
    private Long totalCount;
}
