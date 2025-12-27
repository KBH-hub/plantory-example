package com.zero.plantoryprojectbe.member;

import com.zero.plantoryprojectbe.global.plantoryEnum.ImageTargetType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberImageRepository extends JpaRepository<MemberImage, Long> {
    Optional<MemberImage> findTop1ByMember_MemberIdAndTargetTypeAndDelFlagIsNullOrderByCreatedAtDesc(
            Long member_memberId, String targetType
    );

}

