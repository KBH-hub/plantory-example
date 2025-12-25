package com.zero.plantoryprojectbe.image.service;

import com.zero.plantoryprojectbe.image.dto.ImageDTO;
import com.zero.plantoryprojectbe.global.plantoryEnum.ImageTargetType;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ImageService {

    String uploadProfileImage(Long memberId, MultipartFile file) throws IOException;
    ImageDTO getProfileImage(Long memberId);
    String getProfileImageUrl(Long memberId);
    List<ImageDTO> getImagesByTarget(ImageTargetType targetType, Long targetId);
}
