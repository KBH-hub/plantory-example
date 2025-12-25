package com.zero.plantoryprojectbe.image;

import com.zero.plantoryprojectbe.image.dto.ImageDTO;
import com.zero.plantoryprojectbe.global.plantoryEnum.ImageTargetType;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ImageMapper {

    String selectSharingThumbnail(@Param("targetType") ImageTargetType targetType, @Param("targetId") Long targetId);

    // 단건 조회
    ImageDTO selectImageById(@Param("imageId") Long imageId);
    // 다건 조회
    List<ImageDTO> selectImagesByTarget(@Param("targetType") ImageTargetType targetType, @Param("targetId") Long targetId);

    int insertImage(ImageDTO imageDTO);
    int softDeleteImage(@Param("imageId") Long imageId);
    int softDeleteImagesByTarget(@Param("targetType") ImageTargetType targetType, @Param("targetId") Long targetId);

    // 단건 조회 + 최신에 업데이트된 사진 하나만 객체
    ImageDTO selectLatestProfileImage(@Param("targetType") ImageTargetType targetType,
                                      @Param("memberId") Long memberId);

    // 단건 조회 + 최신에 업데이트된 사진 하나의 URL주소만 리턴
    String getProfileImageUrl(Long memberId);

}
