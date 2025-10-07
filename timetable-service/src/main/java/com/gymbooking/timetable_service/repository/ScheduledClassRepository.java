package com.gymbooking.timetable_service.repository;

import com.gymbooking.timetable_service.entities.ScheduledClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScheduledClassRepository extends JpaRepository<ScheduledClass, Long> {

    List<ScheduledClass> findByClassId(Long classId);
    List<ScheduledClass> findByInstructorId(Long instructorId);
}
