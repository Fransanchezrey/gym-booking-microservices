package com.gymbooking.classcatalog.service;

import com.gymbooking.classcatalog.entities.FitnessClass;
import com.gymbooking.classcatalog.exception.BusinessRuleException;
import com.gymbooking.classcatalog.repository.FitnessClassRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FitnessClassServiceImpl implements FitnessClassService {
    @Autowired
    private FitnessClassRepository fitnessClassRepository;

    @Override
    public List<FitnessClass> findAll() {
        return fitnessClassRepository.findAll();
    }

    @Override
    public FitnessClass getClassById(Long id) throws BusinessRuleException {
        return fitnessClassRepository.findById(id)
                .orElseThrow(() -> new BusinessRuleException("1001", "Fitness class not found", HttpStatus.NOT_FOUND));


    }

    @Override
    public FitnessClass save(FitnessClass fitnessClass) throws BusinessRuleException {
        if (fitnessClass.getName() == null || fitnessClass.getName().trim().isEmpty()) {
            throw new BusinessRuleException("1002", "El nombre de la clase no puede estar vacío", HttpStatus.BAD_REQUEST);
        }
        else
            return fitnessClassRepository.save(fitnessClass);
    }

    @Override
    public void deleteById(Long id) throws BusinessRuleException {
        if (!fitnessClassRepository.existsById(id)) {
            throw new BusinessRuleException("1001", "Fitness class not found", HttpStatus.NOT_FOUND);
        }
        fitnessClassRepository.deleteById(id);
    }

}
