package com.zero.plantoryprojectbe.reportManagement.mapper;

import com.zero.plantoryprojectbe.reportManagement.ReportManagementMapper;
import com.zero.plantoryprojectbe.reportManagement.dto.ReportManagementDetailResponse;
import com.zero.plantoryprojectbe.reportManagement.dto.ReportManagementResponse;
import com.zero.plantoryprojectbe.reportManagement.dto.ReportManagementSearchRequest;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
@Slf4j
@Transactional
class ReportManagementMapperTest {

    @Autowired
    ReportManagementMapper reportManagementMapper;

    @Test
    @DisplayName("일반 조회")
    void selectReportListTest() {
        ReportManagementSearchRequest vo = ReportManagementSearchRequest.builder()
                .limit(10)
                .offset(0)
                .build();

        List<ReportManagementResponse> result = reportManagementMapper.selectReportList(vo);

        log.info("result={}", result);
    }

    @Test
    @DisplayName("신고 내용으로 검색")
    void selectReportListByKeywordTest() {
        ReportManagementSearchRequest vo = ReportManagementSearchRequest.builder()
                .keyword("약속")
                .status(null)
                .limit(10)
                .offset(0)
                .build();

        List<ReportManagementResponse> result = reportManagementMapper.selectReportList(vo);

        log.info("result={}", result);
    }

    @Test
    @DisplayName("처리 완료 필터링 조회")
    void selectReportListByStatusTest() {
        ReportManagementSearchRequest vo = ReportManagementSearchRequest.builder()
                .keyword(null)
                .status("true")
                .limit(10)
                .offset(0)
                .build();

        List<ReportManagementResponse> result = reportManagementMapper.selectReportList(vo);

        log.info("result={}", result);
    }

    @Test
    @DisplayName("신고 목록 삭제 처리")
    void deleteReportsTest() {
        List<Long> ids = List.of(1L, 2L);

        int result = reportManagementMapper.deleteReports(ids);

        log.info(String.valueOf(result));
    }

    @Test
    @DisplayName("신고 내용 상세")
    void selectReportDetailTest() {
        Long reportId = 3L;

        ReportManagementDetailResponse result = reportManagementMapper.selectReportDetail(reportId);

        log.info("result={}", result);
    }

    @Test
    @DisplayName("처리 의견 입력")
    void insertAdminMemoTest() {
        ReportManagementResponse vo = ReportManagementResponse.builder()
                .reportId(18L)
                .adminMemo("조치하였습니다")
                .status("true")
                .reportId(7L)
                .build();

        int result = reportManagementMapper.insertAdminMemo(vo);

        log.info(String.valueOf(result));
    }

    @Test
    @DisplayName("활동 정지 처리")
    void updateStopDayTest() {
        Long memberId = 2L;
        int days = 7;

        int result = reportManagementMapper.updateStopDay(memberId, days);

        log.info(String.valueOf(result));
    }

}