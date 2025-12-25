package com.zero.plantoryprojectbe.reportManagement;

import com.zero.plantoryprojectbe.reportManagement.dto.ReportManagementDetailResponse;
import com.zero.plantoryprojectbe.reportManagement.dto.ReportManagementResponse;
import com.zero.plantoryprojectbe.reportManagement.dto.ReportManagementSearchRequest;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ReportManagementMapper {
    List<ReportManagementResponse> selectReportList(ReportManagementSearchRequest request);
    int selectReportTotalCount(ReportManagementSearchRequest request);
    ReportManagementDetailResponse selectReportDetail(@Param("reportId") Long reportId);
    int insertAdminMemo(ReportManagementResponse vo);
    int updateStopDay(@Param("memberId") Long memberId, @Param("days") int days);
    int deleteReports(@Param("ids") List<Long> ids);
}
