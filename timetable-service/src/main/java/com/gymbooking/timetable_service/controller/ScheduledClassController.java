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
import java.util.Optional;

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

    @GetMapping("/{id}")
    public ResponseEntity<ScheduledClassResponse> getScheduledClassById(@PathVariable Long id) throws BusinessRuleException {
        ScheduledClass scheduledClass = scheduledClassService.findById(id)
                .orElseThrow(() -> new BusinessRuleException("404", "Clase agendada no encontrada", org.springframework.http.HttpStatus.NOT_FOUND));
        ScheduledClassResponse response = toResponse(scheduledClass);
        return ResponseEntity.ok(response);
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

    @PatchMapping("/{id}/spots")
    public ResponseEntity<ScheduledClass> updateSpotsAvailable(
            @PathVariable Long id,
            @RequestParam("spots") int spots) throws BusinessRuleException {
        ScheduledClass updatedClass = scheduledClassService.updateSpotsAvailable(id, spots)
                .orElseThrow(() -> new BusinessRuleException("404", "Clase agendada no encontrada", org.springframework.http.HttpStatus.NOT_FOUND));
        return ResponseEntity.ok(updatedClass);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteScheduledClass(@PathVariable("id") Long id) throws BusinessRuleException {
        scheduledClassService.deleteScheduledClassById(id);
        return ResponseEntity.noContent().build();
    }


}
