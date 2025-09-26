package com.gymbooking.classcatalog.service;

import com.gymbooking.classcatalog.entities.FitnessClass;
import com.gymbooking.classcatalog.exception.BusinessRuleException;

import java.util.List;
import java.util.Optional;

public interface FitnessClassService {

    List<FitnessClass> findAll();

    FitnessClass getClassById(Long id) throws BusinessRuleException;

    FitnessClass save(FitnessClass fitnessClass);

    void deleteById(Long id) throws BusinessRuleException;
}
