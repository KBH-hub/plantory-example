package com.zero.plantoryprojectbe.myPlant;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "myplant")
@Getter
@NoArgsConstructor
public class MyPlant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "myplant_id", nullable = false)
    private Long myplantId;

    @Column(name = "member_id", nullable = false)
    private Long memberId;

    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Column(name = "type", length = 50)
    private String type;

    @Column(name = "start_at")
    private LocalDateTime startAt;

    @Column(name = "end_date")
    private LocalDateTime endDate;

    @Column(name = "interval")
    private Integer interval;

    @Column(name = "soil", length = 100)
    private String soil;

    @Column(name = "temperature", length = 50)
    private String temperature;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "del_flag")
    private LocalDateTime delFlag;

    public MyPlant(
            Long memberId,
            String name,
            String type,
            LocalDateTime startAt,
            LocalDateTime endDate,
            Integer interval,
            String soil,
            String temperature
    ) {
        this.memberId = memberId;
        this.name = name;
        this.type = type;
        this.startAt = startAt;
        this.endDate = endDate;
        this.interval = interval;
        this.soil = soil;
        this.temperature = temperature;
    }

    @PrePersist
    public void prePersist() {
        if (this.createdAt == null) {
            this.createdAt = LocalDateTime.now();
        }
    }
}
