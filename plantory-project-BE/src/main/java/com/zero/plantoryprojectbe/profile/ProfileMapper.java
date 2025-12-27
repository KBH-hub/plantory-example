package com.zero.plantoryprojectbe.profile;

import com.zero.plantoryprojectbe.profile.dto.ProfileInfoResponse;
import com.zero.plantoryprojectbe.profile.dto.ProfileUpdateRequest;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ProfileMapper {
    ProfileInfoResponse selectProfileInfo(Long memberId);
    int updateNoticeEnabled(@Param("memberId") Long memberId, @Param("enabled") int enabled);
    int updateProfileInfo(ProfileUpdateRequest request);
    int deleteMemberById(Long memberId);
}