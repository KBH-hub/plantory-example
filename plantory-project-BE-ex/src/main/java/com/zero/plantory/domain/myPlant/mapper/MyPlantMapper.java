package com.zero.plantory.domain.myPlant.mapper;

import com.zero.plantory.domain.myPlant.dto.MyPlantRequest;
import com.zero.plantory.domain.myPlant.dto.MyPlantResponse;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MyPlantMapper {

    List<MyPlantResponse> selectMyPlantList(@Param("memberId") Long memberId, @Param("name") String name, @Param("limit") int limit, @Param("offset") int offset);
    int insertMyPlant(MyPlantRequest vo);
    int updateMyPlant(MyPlantRequest vo);
    int deletePlant(Long myplantId);
}
