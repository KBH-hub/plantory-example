package com.zero.plantoryprojectbe.reportManagement.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
@Schema(description = "ID 목록 요청 DTO")
public class IdListRequest {

    @Schema(description = "대상 ID 목록", example = "[1,2,3]")
    private List<Long> ids;
}
