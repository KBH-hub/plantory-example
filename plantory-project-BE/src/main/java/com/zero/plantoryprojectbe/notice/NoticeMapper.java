package com.zero.plantoryprojectbe.notice;

import com.zero.plantoryprojectbe.notice.dto.NoticeDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface NoticeMapper {
    List<NoticeDTO> selectNoticesByReceiver(@Param("receiverId") Long receiverId);
    int insertNotice(NoticeDTO noticeDTO);
    int updateNoticeReadFlag(Long noticeId);
    int deleteAllNotice(@Param("receiverId") Long receiverId);
}
