package com.gymbooking.timetable_service.service;


import com.gymbooking.timetable_service.entities.ScheduledClass;
import com.gymbooking.timetable_service.exception.BusinessRuleException;
import com.gymbooking.timetable_service.repository.ScheduledClassRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ScheduledClassServiceImpl implements ScheduledClassService {

    @Autowired
    private ScheduledClassRepository scheduledClassRepository;

    @Override
    public void deleteScheduledClassById(Long id) throws BusinessRuleException {
        if (!scheduledClassRepository.existsById(id)) {
            throw new  BusinessRuleException("1003", "Member not found for id: " + id, HttpStatus.NOT_FOUND);
        }
        scheduledClassRepository.deleteById(id);
    }

    @Override
    public List<ScheduledClass> findByClassId(Long classId) throws BusinessRuleException {
        List<ScheduledClass> result = scheduledClassRepository.findByClassId(classId);
        if (result.isEmpty()) {
            throw new BusinessRuleException("1003", "No se encontraron clases para classId: " + classId, HttpStatus.NOT_FOUND);
        }
        return result;
    }

    @Override
    public List<ScheduledClass> findByInstructorId(Long instructorId) throws BusinessRuleException {
        List<ScheduledClass> result = scheduledClassRepository.findByInstructorId(instructorId);
        if (result.isEmpty()) {
            throw new BusinessRuleException("1003", "No se encontraron clases para classId: " + instructorId, HttpStatus.NOT_FOUND);
        }
        return result;
    }

    @Override
    public ScheduledClass save(ScheduledClass scheduledClass) {
        return scheduledClassRepository.save(scheduledClass);
    }

    @Override
    public List<ScheduledClass> findAllBetweenDates(LocalDate fromDate, LocalDate toDate) throws BusinessRuleException {
        List<ScheduledClass> result = scheduledClassRepository.findAll().stream()
                .filter(sc -> {
                    LocalDate classDate = sc.getStartTime().toLocalDate();
                    return !classDate.isBefore(fromDate) && !classDate.isAfter(toDate);
                })
                .toList();
        if (result.isEmpty()) {
            throw new BusinessRuleException("1003", "No se encontraron clases en el rango de fechas especificado", HttpStatus.NOT_FOUND);
        }
        return result;
    }

}
