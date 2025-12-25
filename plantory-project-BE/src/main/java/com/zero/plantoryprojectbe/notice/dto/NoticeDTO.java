package com.zero.plantoryprojectbe.notice.dto;

import com.zero.plantoryprojectbe.global.plantoryEnum.NoticeTargetType;
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
@Schema(description = "알림 정보")
public class NoticeDTO {

    @Schema(description = "알림 ID", example = "10")
    private Long noticeId;

    @Schema(description = "수신 회원 ID", example = "8")
    private Long receiverId;

    @Schema(description = "알림 대상 타입", example = "SHARING")
    private NoticeTargetType targetType;

    @Schema(description = "알림 대상 ID", example = "15")
    private Long targetId;

    @Schema(description = "알림 내용", example = "나눔에 새로운 댓글이 등록되었습니다.")
    private String content;

    @Schema(description = "읽음 일시", example = "2025-01-10T11:00:00")
    private Date readFlag;

    @Schema(description = "생성 일시", example = "2025-01-10T10:30:00")
    private Date createdAt;

    @Schema(description = "삭제 일시", example = "2025-01-11T09:00:00")
    private Date delFlag;
}