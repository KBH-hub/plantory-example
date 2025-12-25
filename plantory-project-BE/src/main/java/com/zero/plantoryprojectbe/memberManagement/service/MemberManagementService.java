package com.zero.plantoryprojectbe.memberManagement.service;

import com.zero.plantoryprojectbe.memberManagement.dto.MemberManagementResponse;

import java.util.List;


public interface MemberManagementService {
    List<MemberManagementResponse> getMemberList(String keyword, int limit, int offset);
}
