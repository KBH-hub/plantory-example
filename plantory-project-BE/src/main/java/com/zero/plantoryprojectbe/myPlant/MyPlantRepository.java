package com.zero.plantoryprojectbe.myPlant;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

public interface MyPlantRepository extends JpaRepository<MyPlant, Long> {

    @Query("""
        select p
        from MyPlant p
        where p.delFlag is null
          and p.memberId = :memberId
          and p.name like concat('%', :name, '%')
        order by p.createdAt desc
    """)
    Page<MyPlant> selectMyPlantList(
            @Param("memberId") Long memberId,
            @Param("name") String name,
            Pageable pageable
    );

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query(value = """
    UPDATE myplant
    SET member_id = :memberId,
        name = :name,
        type = :type,
        start_at = :startAt,
        end_date = :endDate,
        `interval` = :interval,
        soil = :soil,
        temperature = :temperature
    WHERE myplant_id = :myplantId
""", nativeQuery = true)
    int updateMyPlant(
            @Param("myplantId") Long myplantId,
            @Param("memberId") Long memberId,
            @Param("name") String name,
            @Param("type") String type,
            @Param("startAt") LocalDateTime startAt,
            @Param("endDate") LocalDateTime endDate,
            @Param("interval") Integer interval,
            @Param("soil") String soil,
            @Param("temperature") String temperature
    );

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("""
        update MyPlant p
        set p.delFlag = :now
        where p.myplantId = :myplantId
    """)
    int deletePlant(@Param("myplantId") Long myplantId, @Param("now") LocalDateTime now);
}
