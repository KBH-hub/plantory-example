package com.zero.plantoryprojectbe.message.service;

import com.zero.plantoryprojectbe.message.dto.MessageListResponse;
import com.zero.plantoryprojectbe.message.dto.MessageRequest;
import com.zero.plantoryprojectbe.message.dto.MessageResponse;
import com.zero.plantoryprojectbe.message.dto.MessageSearchRequest;

import java.util.List;

public interface MessageService {
    List<MessageListResponse> getMessageList(MessageSearchRequest request);
    int removeMessages(List<Long> messageIds);
    int removeSenderMessages(List<Long> messageIds);
    MessageResponse findMessageWriteInfo(Long senderId, String targetType, Long targetId);
    int registerMessage(MessageRequest request);
    MessageResponse findMessageDetail(Long messageId, Long viewerId);
}
