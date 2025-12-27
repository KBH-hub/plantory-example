package com.zero.plantoryprojectbe.member.service;

import com.zero.plantoryprojectbe.profile.dto.MemberSignUpRequest;

public interface MemberService {
    boolean isDuplicateMembername(String membername);
    boolean isDuplicateNickname(String nickname);
    void signUp(MemberSignUpRequest request);
}
