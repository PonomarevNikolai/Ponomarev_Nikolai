package com.ponomarevnikolaidiplom.repozitories;

import com.ponomarevnikolaidiplom.entities.Doctor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {
   Doctor findDoctorById(Long id);
   Page findAll(Pageable pageable);
}