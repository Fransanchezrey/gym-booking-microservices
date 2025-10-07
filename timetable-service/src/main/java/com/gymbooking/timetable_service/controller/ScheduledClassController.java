package com.gymbooking.timetable_service.controller;

import com.gymbooking.timetable_service.client.ClassCatalogClient;
import com.gymbooking.timetable_service.dto.ScheduledClassResponse;
import com.gymbooking.timetable_service.entities.ScheduledClass;
import com.gymbooking.timetable_service.exception.BusinessRuleException;
import com.gymbooking.timetable_service.service.ScheduledClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/timetable")
public class ScheduledClassController {

    @Autowired
    private ScheduledClassService scheduledClassService;

    @Autowired
    private ClassCatalogClient classCatalogClient;

    private ScheduledClassResponse toResponse(ScheduledClass scheduledClass) {
        ScheduledClassResponse response = new ScheduledClassResponse();
        response.setId(scheduledClass.getId());
        response.setStartTime(scheduledClass.getStartTime().toString());
        response.setEndTime(scheduledClass.getEndTime().toString());
        response.setSpotsAvailable(scheduledClass.getSpotsAvailable());
        response.setClassName(classCatalogClient.getClassNameById(scheduledClass.getClassId()));
        response.setInstructorName(""); // Preparado para el futuro
        return response;
    }

    @GetMapping
    public ResponseEntity<List<ScheduledClassResponse>> getAllScheduledClasses(
            @RequestParam("fromDate") String fromDate,
            @RequestParam("toDate") String toDate) throws BusinessRuleException {
        List<ScheduledClass> classes = scheduledClassService.findAllBetweenDates(
                LocalDate.parse(fromDate),
                LocalDate.parse(toDate)
        );
        List<ScheduledClassResponse> response = classes.stream()
                .map(this::toResponse)
                .toList();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/by-instructor")
    public ResponseEntity<List<ScheduledClassResponse>> getClassesByInstructorId(
            @RequestParam("instructorId") Long instructorId) throws BusinessRuleException {
        List<ScheduledClass> classes = scheduledClassService.findByInstructorId(instructorId);
        List<ScheduledClassResponse> response = classes.stream()
                .map(this::toResponse)
                .toList();
        return ResponseEntity.ok(response);
    }



    @GetMapping("/by-class")
    public ResponseEntity<List<ScheduledClassResponse>> getClassesByClassId(
            @RequestParam("classId") Long classId) throws BusinessRuleException {
        List<ScheduledClass> classes = scheduledClassService.findByClassId(classId);
        List<ScheduledClassResponse> response = classes.stream()
                .map(this::toResponse)
                .toList();
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<ScheduledClass> createScheduledClass(@RequestBody ScheduledClass scheduledClass) {
        ScheduledClass savedClass = scheduledClassService.save(scheduledClass);
        return ResponseEntity.status(201).body(savedClass);
    }


}
