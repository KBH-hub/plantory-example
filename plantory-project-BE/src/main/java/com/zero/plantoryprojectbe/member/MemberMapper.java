package com.zero.plantoryprojectbe.member;

import com.zero.plantoryprojectbe.profile.dto.MemberResponse;
import com.zero.plantoryprojectbe.profile.dto.MemberRequest;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface MemberMapper {
    int countByMembername(@Param("membername") String membername);
    int countByNickname(@Param("nickname") String nickname);
    int insertMember(MemberRequest memberRequest);
    MemberResponse selectByMembername(@Param("membername") String membername);
    MemberResponse selectByMemberId(@Param("memberId") Long memberId);
    void resetStopDay(Long memberId);
}

