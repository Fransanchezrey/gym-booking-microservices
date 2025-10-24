package com.gymbooking.timetable_service.service;

import com.gymbooking.timetable_service.entities.ScheduledClass;
import com.gymbooking.timetable_service.exception.BusinessRuleException;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ScheduledClassService {

    List<ScheduledClass> findAllBetweenDates(LocalDate fromDate, LocalDate toDate) throws BusinessRuleException;

    List<ScheduledClass> findByClassId(Long classId) throws BusinessRuleException;

    List<ScheduledClass> findByInstructorId(Long instructorId) throws BusinessRuleException;

    ScheduledClass save(ScheduledClass scheduledClass);

    Optional<ScheduledClass> findById(Long id) throws BusinessRuleException;

    void deleteScheduledClassById(Long id) throws BusinessRuleException;

    Optional<ScheduledClass> updateSpotsAvailable(Long id, Integer spotsAvailable) throws BusinessRuleException;
}
