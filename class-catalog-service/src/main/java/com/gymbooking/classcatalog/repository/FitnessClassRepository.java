package com.gymbooking.classcatalog.repository;

import com.gymbooking.classcatalog.entities.FitnessClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FitnessClassRepository extends JpaRepository<FitnessClass, Long> {
}
