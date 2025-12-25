package com.zero.plantoryprojectbe.memberManagement.service;

import com.zero.plantoryprojectbe.memberManagement.MemberManagementMapper;
import com.zero.plantoryprojectbe.memberManagement.dto.MemberManagementResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberManagementServiceImpl implements MemberManagementService{

    private final MemberManagementMapper memberManagementMapper;

    @Override
    public List<MemberManagementResponse> getMemberList(String keyword, int limit, int offset) {

        return memberManagementMapper.selectMemberList(keyword, limit, offset);
    }

}
