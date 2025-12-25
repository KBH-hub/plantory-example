package com.zero.plantoryprojectbe.profile;

import com.zero.plantoryprojectbe.profile.dto.MemberResponse;
import com.zero.plantoryprojectbe.profile.dto.ProfileInfoResponse;
import com.zero.plantoryprojectbe.profile.dto.ProfileUpdateRequest;
import com.zero.plantoryprojectbe.profile.dto.PublicProfileResponse;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ProfileMapper {
    ProfileInfoResponse selectProfileInfo(Long memberId);
    int updateNoticeEnabled(@Param("memberId") Long memberId, @Param("enabled") int enabled);
    int updateProfileInfo(ProfileUpdateRequest request);
    int deleteMemberById(Long memberId);
    PublicProfileResponse selectPublicProfile(Long memberId);
    MemberResponse selectByMemberId(@Param("memberId") Long memberId);
    int updatePassword(@Param("password") String password, @Param("memberId") Long memberId);
}