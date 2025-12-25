package com.zero.plantoryprojectbe.message;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

public interface MessageRepository extends JpaRepository<Message, Long> {

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Transactional
    @Query("update Message m set m.readFlag = :readTime where m.messageId = :messageId")
    int updateReadFlag(@Param("messageId") Long messageId,
                       @Param("readTime") LocalDateTime readTime);
}
