package com.zero.plantoryprojectbe.plantingCalendar.service;

import com.zero.plantoryprojectbe.image.dto.ImageDTO;
import com.zero.plantoryprojectbe.plantingCalendar.dto.DiaryRequest;
import com.zero.plantoryprojectbe.plantingCalendar.dto.DiaryResponse;
import com.zero.plantoryprojectbe.plantingCalendar.dto.MyPlantDiaryResponse;
import com.zero.plantoryprojectbe.plantingCalendar.dto.PlantingCalendarResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

public interface PlantingCalenderService {
    int updatePlantWateringCheck(Long wateringId);
    int removePlantWatering(Long myplantId, Long removerId);
    List<PlantingCalendarResponse> getWateringCalendar(Long memberId, LocalDateTime startDate, LocalDateTime endDate);
    List<PlantingCalendarResponse> getDiaryCalendar(Long memberId, LocalDateTime startDate, LocalDateTime endDate);
    DiaryResponse findDiaryUpdateInfo(Long diaryId);
    List<ImageDTO> findDiaryUpdateImageInfo(Long diaryId);
    int updateDiary(DiaryRequest request, List<ImageDTO> delImgList, List<MultipartFile> files, Long memberId) throws IOException;
    List<MyPlantDiaryResponse> getMyPlant(Long memberId);
    int registerDiary(DiaryRequest request, List<MultipartFile> files, Long memberId) throws IOException;
    int removeDiary(Long diaryId);
    int registerWatering(int batchSize);
    int sendSMS(int batchSize);
}
