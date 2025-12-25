package com.zero.plantoryprojectbe.report;

import com.zero.plantoryprojectbe.report.dto.NameListResponse;
import com.zero.plantoryprojectbe.report.dto.ReportRequest;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ReportMapper {
    List<NameListResponse> selectUserIdByNickname(@Param("nickname") String nickname);
    @Options(useGeneratedKeys = true, keyProperty = "reportId", keyColumn = "report_id")
    int insertReport(ReportRequest request);
}
