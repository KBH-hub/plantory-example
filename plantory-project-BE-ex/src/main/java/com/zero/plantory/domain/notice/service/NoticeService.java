package com.zero.plantory.domain.notice.service;

import com.zero.plantory.global.dto.NoticeDTO;

import java.util.List;

public interface NoticeService {
    List<NoticeDTO> getNoticeByReceiver(Long receiverId);
    int registerNotice(NoticeDTO request);
    int  updateNoticeReadFlag(Long noticeId);
    int removeAllNotice (Long receiverId);

}
