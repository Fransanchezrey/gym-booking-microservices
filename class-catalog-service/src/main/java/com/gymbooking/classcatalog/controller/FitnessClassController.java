package com.gymbooking.classcatalog.controller;

import com.gymbooking.classcatalog.entities.FitnessClass;
import com.gymbooking.classcatalog.exception.BusinessRuleException;
import com.gymbooking.classcatalog.service.FitnessClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/classes")
public class FitnessClassController {

    @Autowired
    private FitnessClassService fitnessClassService;

    @GetMapping("/{id}")
    public ResponseEntity<FitnessClass> getClassById(@PathVariable Long id) throws BusinessRuleException {
        return ResponseEntity.ok(fitnessClassService.getClassById(id)) ;

    }

    @GetMapping
    public ResponseEntity<List<FitnessClass>> listAll()
    {
        List<FitnessClass> classes = fitnessClassService.findAll();
        if (classes.isEmpty()){
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(classes);
        }
    }

    @PostMapping
    public ResponseEntity<FitnessClass> create(@RequestBody FitnessClass fitnessClass) {
        FitnessClass savedClass = fitnessClassService.save(fitnessClass);
        return ResponseEntity.ok(savedClass);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClassById(@PathVariable Long id) throws BusinessRuleException {
        fitnessClassService.deleteById(id);
        return ResponseEntity.noContent().build();
    }


}
