package com.zero.plantoryprojectbe.member;

import com.zero.plantoryprojectbe.global.plantoryEnum.ImageTargetType;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static com.zero.plantoryprojectbe.global.plantoryEnum.ImageTargetType.PROFILE;

@Slf4j
@SpringBootTest
@Transactional
class MemberImageRepositoryTest {
        @Autowired
        MemberImageRepository memberImageRepository;

        @Test
        void findTop1ByMemberId_latest_not_deleted() {
            Long memberId = 1L;

            var result = memberImageRepository
                    .findTop1ByMember_MemberIdAndTargetTypeAndDelFlagIsNullOrderByCreatedAtDesc(memberId, String.valueOf(ImageTargetType.PROFILE));

            System.out.println("result present? " + result.isPresent());
            result.ifPresent(img -> {
                System.out.println("imageId=" + img.getImageId());
                System.out.println("fileUrl=" + img.getFileUrl());
                System.out.println("createdAt=" + img.getCreatedAt());
                System.out.println("delFlag=" + img.getDelFlag());
            });
        }
}