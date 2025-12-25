package com.zero.plantoryprojectbe.plantingCalendar.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "SMS 발송 요청")
public class SMSRequest {

    @Schema(description = "수신 번호", example = "01012345678")
    private String to;

    @Schema(description = "발신 번호", example = "01098765432")
    private String from;

    @Schema(description = "메시지 내용", example = "오늘 물 주기 알림입니다.")
    private String text;
}
