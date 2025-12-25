package com.zero.plantoryprojectbe.memberManagement;

import com.zero.plantoryprojectbe.global.plantoryEnum.DeleteTargetType;
import com.zero.plantoryprojectbe.memberManagement.dto.MemberManagementResponse;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


@Mapper
public interface MemberManagementMapper {
    List<MemberManagementResponse> selectMemberList(@Param("keyword") String keyword, @Param("limit") int limit, @Param("offset") int offset);
    int deleteContent(@Param("targetType") DeleteTargetType targetType,@Param("targetId") Long targetId);
}
