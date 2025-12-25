package com.zero.plantoryprojectbe.reportManagement.service;

import com.zero.plantoryprojectbe.reportManagement.dto.ReportManagementDetailResponse;
import com.zero.plantoryprojectbe.reportManagement.dto.ReportManagementPageResponse;
import com.zero.plantoryprojectbe.reportManagement.dto.ReportManagementSearchRequest;

import java.util.List;


public interface ReportManagementService {
    ReportManagementPageResponse getReporManagmentList(ReportManagementSearchRequest request);
    int deleteReporManagmentList(List<Long> ids);
    ReportManagementDetailResponse getReportDetail(Long reportId);
    void processReport(Long reportId, Long memberId, String adminMemo, int stopDays);
}