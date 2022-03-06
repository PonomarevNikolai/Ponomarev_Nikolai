package com.ponomarevnikolaidiplom.repozitories;

import com.ponomarevnikolaidiplom.entities.MedicalBill;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicalBillRepository extends JpaRepository<MedicalBill, Long> {
}