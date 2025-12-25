package com.zero.plantoryprojectbe.message;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table(name = "message")
public class Message {

    @Id
    @Column(name = "message_id")
    private Long messageId;

    @Column(name = "read_flag")
    private LocalDateTime readFlag;
}
