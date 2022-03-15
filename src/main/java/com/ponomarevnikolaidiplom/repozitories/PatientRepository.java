package com.ponomarevnikolaidiplom.repozitories;

import com.ponomarevnikolaidiplom.entities.Patient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<Patient, Long> {
    Page findAll(Pageable pageable);
}