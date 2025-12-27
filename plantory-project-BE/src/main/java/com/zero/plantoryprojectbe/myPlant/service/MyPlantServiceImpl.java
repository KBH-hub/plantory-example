package com.zero.plantoryprojectbe.myPlant.service;

import com.zero.plantoryprojectbe.global.plantoryEnum.ImageTargetType;
import com.zero.plantoryprojectbe.global.utils.StorageUploader;
import com.zero.plantoryprojectbe.image.ImageMapper;
import com.zero.plantoryprojectbe.image.dto.ImageDTO;
import com.zero.plantoryprojectbe.myPlant.MyPlant;
import com.zero.plantoryprojectbe.myPlant.MyPlantMapper;
import com.zero.plantoryprojectbe.myPlant.MyPlantRepository;
import com.zero.plantoryprojectbe.myPlant.dto.MyPlantRequest;
import com.zero.plantoryprojectbe.myPlant.dto.MyPlantResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MyPlantServiceImpl implements MyPlantService {

    private final MyPlantRepository myPlantRepository;
    private final ImageMapper imageMapper;
    private final StorageUploader storageUploader;
    private final MyPlantMapper myPlantMapper;

    @Override
    public List<MyPlantResponse> getMyPlantList(Long memberId, String name, int limit, int offset) {

        String keyword = (name == null) ? "" : name;

        int safeLimit = (limit <= 0) ? 10 : limit;
        int page = (offset < 0) ? 0 : (offset / safeLimit);
        Pageable pageable = PageRequest.of(page, safeLimit);

        Page<MyPlant> pageResult = myPlantRepository.selectMyPlantList(memberId, keyword, pageable);
        int totalCount = (int) pageResult.getTotalElements();

        List<MyPlantResponse> resultList = new ArrayList<>();
        for (MyPlant p : pageResult.getContent()) {

            List<ImageDTO> images = imageMapper.selectImagesByTarget(ImageTargetType.MYPLANT, p.getMyplantId());
            String url = images.isEmpty() ? null : images.get(0).getFileUrl();
            Long imageId = images.isEmpty() ? null : images.get(0).getImageId();

            MyPlantResponse dto = MyPlantResponse.builder()
                    .myplantId(p.getMyplantId())
                    .memberId(p.getMemberId())
                    .name(p.getName())
                    .type(p.getType())
                    .startAt(toDate(p.getStartAt()))
                    .endDate(toDate(p.getEndDate()))
                    .interval(p.getInterval() == null ? 0 : p.getInterval())
                    .soil(p.getSoil())
                    .temperature(p.getTemperature())
                    .imageUrl(url)
                    .imageId(imageId)
                    .createdAt(toDate(p.getCreatedAt()))
                    .delFlag(toDate(p.getDelFlag()))
                    .totalCount(totalCount)
                    .build();

            resultList.add(dto);
        }

        return resultList;
    }

    @Override
    @Transactional
    public int registerMyPlant(MyPlantRequest request, MultipartFile file, Long memberId) throws IOException {
        if (request.getName() == null || request.getName().equals("")) {
            throw new IllegalArgumentException("내 식물 등록 필수값(식물 이름) 누락");
        }

        int insertMyplant = myPlantMapper.insertMyPlant(request);
        if (insertMyplant == 0) {
            throw new IllegalStateException("관찰일지 저장 실패");
        }

        Long myplantId = request.getMyplantId();
        if (myplantId == null) {
            throw new IllegalStateException("myplantId 미할당");
        }

        if (file == null) {
            return insertMyplant;
        }

        int insertedImages = 0;
        String url = storageUploader.uploadFile(file);

        ImageDTO image = ImageDTO.builder()
                .memberId(memberId)
                .targetType(ImageTargetType.MYPLANT)
                .targetId(myplantId)
                .fileUrl(url)
                .fileName(file.getOriginalFilename())
                .build();

        insertedImages += imageMapper.insertImage(image);

        if (insertedImages == 1) {
            return 2;
        }

        throw new IllegalArgumentException("내 식물 등록 실패");
    }

    @Override
    @Transactional
    public int updateMyPlant(MyPlantRequest request, Long delFile, MultipartFile file, Long memberId) throws IOException {

        if (request.getName() == null || request.getName().isBlank()) {
            throw new IllegalArgumentException("내 식물 수정 필수값(식물 이름) 누락");
        }

        int result = 0;

        result += myPlantRepository.updateMyPlant(
                request.getMyplantId(),
                memberId,
                request.getName(),
                request.getType(),
                request.getStartAt(),
                request.getEndDate(),
                request.getInterval(),
                request.getSoil(),
                request.getTemperature()
        );

        int fileCount = 0;
        fileCount += (delFile == null) ? 0 : 1;
        fileCount += (file == null) ? 0 : 1;

        if (delFile != null) {
            result += imageMapper.softDeleteImage(delFile);
        }

        if (file != null) {
            String url = storageUploader.uploadFile(file);

            ImageDTO image = ImageDTO.builder()
                    .memberId(memberId)
                    .targetType(ImageTargetType.MYPLANT)
                    .targetId(request.getMyplantId())
                    .fileUrl(url)
                    .fileName(file.getOriginalFilename())
                    .build();

            result += imageMapper.insertImage(image);
        }

        if (result == fileCount + 1) {
            return result;
        }

        throw new IllegalStateException("관찰일지 수정 실패(업데이트 누락)");
    }

    @Override
    @Transactional
    public int removePlant(Long myplantId, Long delFile) {

        int result = myPlantRepository.deletePlant(myplantId, LocalDateTime.now());

        if (delFile != null) {
            result += imageMapper.softDeleteImage(delFile);
            if (result < 2) {
                throw new IllegalArgumentException("내 식물 삭제 실패");
            }
        }

        return result;
    }

    private static Date toDate(LocalDateTime t) {
        return (t == null) ? null : Timestamp.valueOf(t);
    }
}
