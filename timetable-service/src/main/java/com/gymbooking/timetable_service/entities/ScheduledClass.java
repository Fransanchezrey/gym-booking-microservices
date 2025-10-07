package com.gymbooking.timetable_service.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class ScheduledClass {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long classId;
    private Long instructorId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Integer capacity;
    private Integer spotsAvailable;
}
