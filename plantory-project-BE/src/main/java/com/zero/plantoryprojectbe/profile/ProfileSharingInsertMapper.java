package com.zero.plantoryprojectbe.profile;

import com.zero.plantoryprojectbe.profile.dto.ProfileSharingResponse;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ProfileSharingInsertMapper {
    List<ProfileSharingResponse> selectInterestSharingList(
            @Param("memberId") Long memberId,
            @Param("keyword") String keyword,
            @Param("limit") int limit,
            @Param("offset") int offset
    );
}

