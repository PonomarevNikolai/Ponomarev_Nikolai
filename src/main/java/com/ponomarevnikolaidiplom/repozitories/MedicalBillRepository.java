package com.ponomarevnikolaidiplom.repozitories;

import com.ponomarevnikolaidiplom.entities.MedicalBill;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicalBillRepository extends JpaRepository<MedicalBill, Long> {
    MedicalBill findMedicalBillById(Long id);
    Page findAll(Pageable pageable);
}